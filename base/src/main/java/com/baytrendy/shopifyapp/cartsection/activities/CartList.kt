package com.baytrendy.shopifyapp.cartsection.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.cartsection.adapters.CartListAdapter
import com.baytrendy.shopifyapp.cartsection.models.CartBottomData
import com.baytrendy.shopifyapp.cartsection.viewmodels.CartListViewModel
import com.baytrendy.shopifyapp.checkoutsection.activities.CheckoutWeblink
import com.baytrendy.shopifyapp.customviews.MageNativeButton
import com.baytrendy.shopifyapp.databinding.DiscountCodeLayoutBinding
import com.baytrendy.shopifyapp.databinding.MCartlistBinding
import com.baytrendy.shopifyapp.loginsection.activity.LoginActivity
import com.baytrendy.shopifyapp.personalised.adapters.PersonalisedAdapter
import com.baytrendy.shopifyapp.personalised.viewmodels.PersonalisedViewModel
import com.baytrendy.shopifyapp.utils.*
import com.baytrendy.shopifyapp.wishlistsection.activities.WishList
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class CartList : NewBaseActivity() {
    @Inject
    lateinit var factory: ViewModelFactory
    private var cartlist: RecyclerView? = null
    private var model: CartListViewModel? = null
    private var personamodel: PersonalisedViewModel? = null
    private var count: Int = 1
    private val TAG = "CartList"

    @Inject
    lateinit var adapter: CartListAdapter

    @Inject
    lateinit var personalisedadapter: PersonalisedAdapter

    @Inject
    lateinit var padapter: PersonalisedAdapter
    private var binding: MCartlistBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_cartlist, group, true)
        cartlist = setLayout(binding!!.cartlist, "vertical")
        cartlist!!.isNestedScrollingEnabled = false
        showTittle(resources.getString(R.string.yourcart))
        showBackButton()
        (application as MyApplication).mageNativeAppComponent!!.doCartListActivityInjection(this)
        model = ViewModelProvider(this, factory).get(CartListViewModel::class.java)
        model!!.context = this
        personamodel = ViewModelProvider(this, factory).get(PersonalisedViewModel::class.java)
        model!!.Response().observe(this, Observer<Storefront.Checkout> { this.consumeResponse(it) })
        if (SplashViewModel.featuresModel.ai_product_reccomendaton) {
            if (Constant.ispersonalisedEnable) {
                model!!.getApiResponse().observe(this, Observer<ApiResponse> { this.consumeResponse(it) })
                model!!.getYouMAyAPiResponse().observe(this, Observer<ApiResponse> { this.Response(it) })
            }
        }
        model!!.message.observe(this, Observer<String> { this.showToast(it) })
        model!!.getGiftCard().observe(this, Observer<Storefront.Mutation> { this.consumeResponseGift(it) })
        model!!.getGiftCardRemove().observe(this, Observer<Storefront.Mutation> { this.consumeResponseGiftRemove(it) })
        model!!.getDiscount().observe(this, Observer<Storefront.Mutation> { this.consumeResponseDiscount(it) })
        if (model!!.cartCount > 0) {
            model!!.prepareCart()
        } else {
            showToast(resources.getString(R.string.emptycart))
            finish()
        }
        binding!!.subtotaltext.textSize = 12f
        binding!!.subtotal.textSize = 12f
        binding!!.taxtext.textSize = 12f
        binding!!.tax.textSize = 12f
        binding!!.proceedtocheck.textSize = 13f
        binding!!.handler = ClickHandler()
    }

    private fun consumeResponseDiscount(it: Storefront.Mutation?) {
        Log.d(TAG, "consumeResponseDiscount: " + it!!.checkoutDiscountCodeApplyV2)
        try {
            val bottomData = CartBottomData()
            bottomData.checkoutId = it!!.checkoutDiscountCodeApplyV2.checkout.id
            Log.d(TAG, "setBottomData: " + bottomData.checkoutId)
            bottomData.subtotaltext = resources.getString(R.string.subtotaltext) + " ( " + model!!.cartCount + " items )"
            bottomData.subtotal = CurrencyFormatter.setsymbol(it!!.checkoutDiscountCodeApplyV2.checkout.subtotalPriceV2.amount, it!!.checkoutDiscountCodeApplyV2.checkout.subtotalPriceV2.currencyCode.toString())
            if (it!!.checkoutDiscountCodeApplyV2.checkout.taxExempt!!) {
                binding!!.taxtext.visibility = View.VISIBLE
                binding!!.tax.visibility = View.VISIBLE
                bottomData.tax = CurrencyFormatter.setsymbol(it!!.checkoutDiscountCodeApplyV2.checkout.totalTaxV2.amount, it!!.checkoutDiscountCodeApplyV2.checkout.totalTaxV2.currencyCode.toString())
            }
            bottomData.grandtotal = CurrencyFormatter.setsymbol(it!!.checkoutDiscountCodeApplyV2.checkout.totalPriceV2.amount, it!!.checkoutDiscountCodeApplyV2.checkout.totalPriceV2.currencyCode.toString())
            bottomData.checkouturl = it!!.checkoutDiscountCodeApplyV2.checkout.webUrl
            binding!!.bottomdata = bottomData
            binding!!.root.visibility = View.VISIBLE
            try {
                if (model!!.isLoggedIn) {
                    Log.i("herer", " " + bottomData.checkoutId)
                    Log.i("herer", "token : " + model?.customeraccessToken?.customerAccessToken)
                    model?.associatecheckout(bottomData.checkoutId, model!!.customeraccessToken?.customerAccessToken)
                    model?.getassociatecheckoutResponse()?.observe(this@CartList, Observer { ClickHandler().getResonse(it) })
                } else {
                    ClickHandler().showPopUp(bottomData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun consumeResponseGiftRemove(it: Storefront.Mutation?) {
        binding!!.applyGiftBut.text = getString(R.string.apply)
        val bottomData = CartBottomData()
        bottomData.checkoutId = it!!.checkoutGiftCardRemoveV2.checkout.id
        Log.d(TAG, "setBottomData: " + bottomData.checkoutId)
        bottomData.subtotaltext = resources.getString(R.string.subtotaltext) + " ( " + model!!.cartCount + " items )"
        bottomData.subtotal = CurrencyFormatter.setsymbol(it!!.checkoutGiftCardRemoveV2.checkout.subtotalPriceV2.amount, it!!.checkoutGiftCardRemoveV2.checkout.subtotalPriceV2.currencyCode.toString())
        if (it!!.checkoutGiftCardRemoveV2.checkout.taxExempt!!) {
            binding!!.taxtext.visibility = View.VISIBLE
            binding!!.tax.visibility = View.VISIBLE
            bottomData.tax = CurrencyFormatter.setsymbol(it!!.checkoutGiftCardRemoveV2.checkout.totalTaxV2.amount, it!!.checkoutGiftCardRemoveV2.checkout.totalTaxV2.currencyCode.toString())
        }
        bottomData.grandtotal = CurrencyFormatter.setsymbol(it!!.checkoutGiftCardRemoveV2.checkout.totalPriceV2.amount, it!!.checkoutGiftCardRemoveV2.checkout.totalPriceV2.currencyCode.toString())
        bottomData.checkouturl = it!!.checkoutGiftCardRemoveV2.checkout.webUrl
        binding!!.bottomdata = bottomData
        binding!!.root.visibility = View.VISIBLE
        showToast(getString(R.string.gift_remove))
    }

    private fun consumeResponseGift(it: Storefront.Mutation?) {
        binding!!.applyGiftBut.text = getString(R.string.remove)
        val bottomData = CartBottomData()
        bottomData.giftcardID = it!!.checkoutGiftCardsAppend.checkout.appliedGiftCards[0].id
        bottomData.checkoutId = it!!.checkoutGiftCardsAppend.checkout.id
        Log.d(TAG, "setBottomData: " + bottomData.checkoutId)
        bottomData.subtotaltext = resources.getString(R.string.subtotaltext) + " ( " + model!!.cartCount + " items )"
        bottomData.subtotal = CurrencyFormatter.setsymbol((it!!.checkoutGiftCardsAppend.checkout.subtotalPriceV2.amount.toDouble() - it!!.checkoutGiftCardsAppend.checkout.appliedGiftCards[0].amountUsedV2.amount.toDouble()).toString(), it!!.checkoutGiftCardsAppend.checkout.subtotalPriceV2.currencyCode.toString())
        if (it!!.checkoutGiftCardsAppend.checkout.taxExempt!!) {
            binding!!.taxtext.visibility = View.VISIBLE
            binding!!.tax.visibility = View.VISIBLE
            bottomData.tax = CurrencyFormatter.setsymbol(it!!.checkoutGiftCardsAppend.checkout.totalTaxV2.amount, it!!.checkoutGiftCardsAppend.checkout.totalTaxV2.currencyCode.toString())
        }
        bottomData.grandtotal = CurrencyFormatter.setsymbol((it!!.checkoutGiftCardsAppend.checkout.totalPriceV2.amount.toDouble() - it!!.checkoutGiftCardsAppend.checkout.appliedGiftCards[0].amountUsedV2.amount.toDouble()).toString(), it!!.checkoutGiftCardsAppend.checkout.totalPriceV2.currencyCode.toString())
        bottomData.checkouturl = it!!.checkoutGiftCardsAppend.checkout.webUrl
        binding!!.bottomdata = bottomData
        binding!!.root.visibility = View.VISIBLE
        showToast(getString(R.string.gift_success))
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@CartList, msg, Toast.LENGTH_LONG).show()
    }

    private fun consumeResponse(reponse: Storefront.Checkout) {
        if (reponse.lineItems.edges.size > 0) {
            showTittle(resources.getString(R.string.yourcart) + " ( " + reponse.lineItems.edges.size + " items )")
            if (adapter!!.data != null) {
                adapter!!.data = reponse.lineItems.edges
                adapter!!.notifyDataSetChanged()
            } else {
                adapter!!.setData(reponse.lineItems.edges, model)
                cartlist!!.adapter = adapter
            }
            setBottomData(reponse)
            invalidateOptionsMenu()
        } else {
            showToast(resources.getString(R.string.emptycart))
            finish()
        }
    }

    private fun consumeResponse(reponse: ApiResponse) {
        when (reponse.status) {
            Status.SUCCESS -> setPersonalisedData(reponse.data!!)
            Status.ERROR -> {
                reponse.error!!.printStackTrace()
                showToast(resources.getString(R.string.errorString))
            }
        }
    }

    private fun Response(reponse: ApiResponse) {
        when (reponse.status) {
            Status.SUCCESS -> setYouMayPersonalisedData(reponse.data!!)
            Status.ERROR -> {
                reponse.error!!.printStackTrace()
                showToast(resources.getString(R.string.errorString))
            }
        }
    }

    private fun setPersonalisedData(data: JsonElement) {
        try {
            val jsondata = JSONObject(data.toString())
            if (jsondata.has("query1")) {
                binding!!.personalisedsection.visibility = View.VISIBLE
                setLayout(binding!!.personalised, "horizontal")
                personamodel!!.setPersonalisedData(jsondata.getJSONObject("query1").getJSONArray("products"), personalisedadapter, model!!.presentCurrency!!, binding!!.personalised)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun setYouMayPersonalisedData(data: JsonElement) {
        try {
            val jsondata = JSONObject(data.toString())
            if (jsondata.has("query1")) {
                binding!!.personalisedsection2.visibility = View.VISIBLE
                setLayout(binding!!.personalised2, "horizontal")
                personamodel!!.setPersonalisedData(jsondata.getJSONObject("query1").getJSONArray("products"), padapter, model!!.presentCurrency!!, binding!!.personalised2)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun setBottomData(checkout: Storefront.Checkout) {
        try {
            val bottomData = CartBottomData()
            bottomData.checkoutId = checkout.id
            Log.d(TAG, "setBottomData: " + bottomData.checkoutId)
            bottomData.subtotaltext = resources.getString(R.string.subtotaltext) + " ( " + model!!.cartCount + " items )"
            bottomData.subtotal = CurrencyFormatter.setsymbol(checkout.subtotalPriceV2.amount, checkout.subtotalPriceV2.currencyCode.toString())
            if (checkout.taxExempt!!) {
                binding!!.taxtext.visibility = View.VISIBLE
                binding!!.tax.visibility = View.VISIBLE
                bottomData.tax = CurrencyFormatter.setsymbol(checkout.totalTaxV2.amount, checkout.totalTaxV2.currencyCode.toString())
            }
            bottomData.grandtotal = CurrencyFormatter.setsymbol(checkout.totalPriceV2.amount, checkout.totalPriceV2.currencyCode.toString())
            bottomData.checkouturl = checkout.webUrl
            binding!!.bottomdata = bottomData
            binding!!.root.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.m_wish, menu)
        val item = menu.findItem(R.id.wish_item)
        item.setActionView(R.layout.m_wishcount)
        val notifCount = item.actionView
        val textView = notifCount.findViewById<TextView>(R.id.count)
        textView.text = "" + model!!.wishListcount
        notifCount.setOnClickListener {
            val mycartlist = Intent(this@CartList, WishList::class.java)
            startActivity(mycartlist)
            Constant.activityTransition(this)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
        count = 1
    }

    inner class ClickHandler {
        fun loadCheckout(view: View, data: CartBottomData) {
            showApplyCouponDialog(data)


        }

        fun applyGiftCard(view: View, bottomData: CartBottomData) {
            if ((view as MageNativeButton).text == getString(R.string.apply)) {
                if (TextUtils.isEmpty(binding!!.giftcardEdt.text.toString().trim())) {
                    binding!!.giftcardEdt.error = getString(R.string.giftcard_validation)
                } else {
                    model!!.applyGiftCard(binding!!.giftcardEdt.text.toString().trim(), bottomData.checkoutId)
                }
            } else if ((view as MageNativeButton).text == getString(R.string.remove)) {
                model!!.removeGiftCard(bottomData.giftcardID, bottomData.checkoutId)
            }
        }

        private fun showApplyCouponDialog(data: CartBottomData) {
            var listdialog = Dialog(this@CartList, R.style.WideDialog)
            listdialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            listdialog.getWindow()!!.setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.MATCH_PARENT)
            var discountCodeLayoutBinding = DataBindingUtil.inflate<DiscountCodeLayoutBinding>(layoutInflater, R.layout.discount_code_layout, null, false)
            listdialog.setContentView(discountCodeLayoutBinding.root)
            discountCodeLayoutBinding.noBut.setOnClickListener {
                try {
                    listdialog.dismiss()
                    if (model!!.isLoggedIn) {
                        Log.i("herer", " " + data.checkoutId)
                        Log.i("herer", "token : " + model?.customeraccessToken?.customerAccessToken)
                        model?.associatecheckout(data.checkoutId, model!!.customeraccessToken?.customerAccessToken)
                        model?.getassociatecheckoutResponse()?.observe(this@CartList, Observer { this.getResonse(it) })
                    } else {
                        showPopUp(data)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            discountCodeLayoutBinding.yesBut.setOnClickListener {
                if (TextUtils.isEmpty(discountCodeLayoutBinding.discountCodeEdt.text.toString().trim())) {
                    discountCodeLayoutBinding.discountCodeEdt.error = getString(R.string.discount_validation)
                } else {
                    model!!.applyDiscount(data.checkoutId, discountCodeLayoutBinding.discountCodeEdt.text.toString())
                    listdialog.dismiss()
                }
            }
            listdialog.show()
        }

        fun getResonse(it: Storefront.Checkout?) {
            if (count == 1) {
                val intent = Intent(this@CartList, CheckoutWeblink::class.java)
                intent.putExtra("link", it?.webUrl)
                intent.putExtra("id", it?.id.toString())
                startActivity(intent)
                Constant.activityTransition(this@CartList)
                count++
            }

        }


        fun showPopUp(data: CartBottomData) {
            try {
                val listDialog = Dialog(this@CartList, R.style.PauseDialog)
                ((Objects.requireNonNull<Window>(listDialog.window).getDecorView() as ViewGroup).getChildAt(0) as ViewGroup)
                        .getChildAt(1)
                        .setBackgroundColor(this@CartList.resources.getColor(R.color.black))
                listDialog.setTitle(Html.fromHtml("<font color='#ffffff'>" + resources.getString(R.string.logintype) + "</font>"))
                val li = this@CartList.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                @SuppressLint("InflateParams") val loginoptions = Objects.requireNonNull(li).inflate(R.layout.m_login_options, null, false)
                val Guest = loginoptions.findViewById<RadioButton>(R.id.Guest)
                val User = loginoptions.findViewById<RadioButton>(R.id.User)
                val id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android")
                Guest.setButtonDrawable(id)
                User.setButtonDrawable(id)
                Guest.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        listDialog.dismiss()
                        val intent = Intent(buttonView.context, CheckoutWeblink::class.java)
                        intent.putExtra("link", data.checkouturl)
                        intent.putExtra("id", data.checkoutId)
                        buttonView.context.startActivity(intent)
                        Constant.activityTransition(buttonView.context)
                    }
                }
                User.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        listDialog.dismiss()
                        val intent = Intent(buttonView.context, LoginActivity::class.java)
                        intent.putExtra("checkout_id", data.checkoutId)
                        buttonView.context.startActivity(intent)
                        Constant.activityTransition(buttonView.context)
                    }
                }
                listDialog.setContentView(loginoptions)
                listDialog.setCancelable(true)
                listDialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
