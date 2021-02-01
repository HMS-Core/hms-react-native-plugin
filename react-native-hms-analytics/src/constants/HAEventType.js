/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

const HAEventType = {
  CREATEPAYMENTINFO: "$CreatePaymentInfo",
  ADDPRODUCT2CART: "$AddProduct2Cart",
  ADDPRODUCT2WISHLIST: "$AddProduct2WishList",
  STARTAPP: "$StartApp",
  STARTCHECKOUT: "$StartCheckout",
  VIEWCAMPAIGN: "$ViewCampaign",
  VIEWCHECKOUTSTEP: "$ViewCheckoutStep",
  WINVIRTUALCOIN: "$WinVirtualCoin",
  COMPLETEPURCHASE: "$CompletePurchase",
  OBTAINLEADS: "$ObtainLeads",
  JOINUSERGROUP: "$JoinUserGroup",
  COMPLETELEVEL: "$CompleteLevel",
  STARTLEVEL: "$StartLevel",
  UPGRADELEVEL: "$UpgradeLevel",
  SIGNIN: "$SignIn",
  SIGNOUT: "$SignOut",
  SUBMITSCORE: "$SubmitScore",
  CREATEORDER: "$CreateOrder",
  REFUNDORDER: "$RefundOrder",
  DELPRODUCTFROMCART: "$DelProductFromCart",
  SEARCH: "$Search",
  VIEWCONTENT: "$ViewContent",
  UPDATECHECKOUTOPTION: "$UpdateCheckoutOption",
  SHARECONTENT: "$ShareContent",
  REGISTERACCOUNT: "$RegisterAccount",
  CONSUMEVIRTUALCOIN: "$ConsumeVirtualCoin",
  STARTTUTORIAL: "$StartTutorial",
  COMPLETETUTORIAL: "$CompleteTutorial",
  OBTAINACHIEVEMENT: "$ObtainAchievement",
  VIEWPRODUCT: "$ViewProduct",
  VIEWPRODUCTLIST: "$ViewProductList",
  VIEWSEARCHRESULT: "$ViewSearchResult",
  UPDATEMEMBERSHIPLEVEL: "$UpdateMembershipLevel",
  FILTRATEPRODUCT: "$FiltrateProduct",
  VIEWCATEGORY: "$ViewCategory",
  UPDATEORDER: "$UpdateOrder",
  CANCELORDER: "$CancelOrder",
  COMPLETEORDER: "$CompleteOrder",
  CANCELCHECKOUT: "$CancelCheckout",
  OBTAINVOUCHER: "$ObtainVoucher",
  CONTACTCUSTOMSERVICE: "$ContactCustomService",
  RATE: "$Rate",
  INVITE: "$Invite",
};

Object.freeze(HAEventType)

export default HAEventType;
