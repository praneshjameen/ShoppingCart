package com.me.sdp.cart.ShoppingCart.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;
import com.me.sdp.cart.ShoppingCart.form.ProductForm;
import com.me.sdp.cart.ShoppingCart.form.UserForm;

public class UserAction extends DispatchAction {

	public ActionForward addToWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			String page = request.getParameter("page");
			if (id != null && page != null) {
				Integer productId = Integer.parseInt(id);
				Integer pageNo = Integer.parseInt(page);
				Integer userId = (Integer) request.getSession().getAttribute("userId");
				System.out.println("Product Id : " + productId);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addToWishlist(userId, productId);
				if (pageNo == 2) {
					return mapping.findForward("viewWishlist");
				} else {
					request.getSession().setAttribute("message", "Added to wishlist Successfully");
					return mapping.findForward("products");
				}
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<ProductForm> wishList = dataAccess.getWishList(userId);
				request.setAttribute("wishLists", wishList);
				return mapping.findForward("wishlist");
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward removeWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			String id = request.getParameter("id");
			String page = request.getParameter("page");
			if (id != null && userId != null && page != null) {
				Integer productId = Integer.parseInt(id);
				Integer pageNo = Integer.parseInt(page);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.removeWishList(userId, productId);
				request.getSession().setAttribute("message", "Removed from wishlist");
				if (pageNo == 2) {
					return mapping.findForward("viewWishlist");
				} else {
					return mapping.findForward("products");
				}
			} else
				return mapping.findForward("success");
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward addToCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			String page = request.getParameter("page");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			Boolean productState = (Boolean) request.getAttribute("productState");
			if (id != null && userId != null && productState != null && page != null) {
				Integer productId = Integer.parseInt(id);
				Integer pageNo = Integer.parseInt(page);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addToCart(userId, productId, productState);
				if (pageNo == 2) {
					String selectAll = request.getParameter("selectAll");
					String[] idList = request.getParameterValues("idList");
					System.out.println(selectAll + idList);
					ArrayList<ProductForm> cartLists = new ArrayList<ProductForm>();
					if (selectAll != null) {
						cartLists = dataAccess.getCartList(userId, null);
						request.setAttribute("selectAll", true);
					} else if (idList != null) {
						ArrayList<Integer> productIds = new ArrayList<Integer>();
						for (int i = 0; i < idList.length; i++) {
							productIds.add(Integer.parseInt(idList[i]));
						}
						cartLists = dataAccess.getCartList(userId, productIds);
						request.setAttribute("selectAll", false);
					}
					request.setAttribute("cartLists", cartLists);
					return mapping.findForward("cart");
				} else {
					request.getSession().setAttribute("message", "Added to Cart Successfully");
					return mapping.findForward("products");
				}
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward removeFromCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (id != null && userId != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				if (request.getParameter("deleteCart") == null) {
					Integer quantity = Integer.parseInt(request.getParameter("quantity"));
					if (quantity == 1) {
						request.getSession().setAttribute("message", "Product removed from cart");
						dataAccess.removeCart(userId, productId, quantity);
					} else {
						dataAccess.removeFromCart(userId, productId);
					}
				} else {
					Integer quantity = Integer.parseInt(request.getParameter("deleteCart"));
					request.getSession().setAttribute("message", "Product removed from cart");
					dataAccess.removeCart(userId, productId, quantity);
				}
				String selectAll = request.getParameter("selectAll");
				String[] idList = request.getParameterValues("idList");
				System.out.println(selectAll + idList);
				ArrayList<ProductForm> cartLists = new ArrayList<ProductForm>();
				if (selectAll != null) {
					cartLists = dataAccess.getCartList(userId, null);
					request.setAttribute("selectAll", true);
				} else if (idList != null) {
					ArrayList<Integer> productIds = new ArrayList<Integer>();
					for (int i = 0; i < idList.length; i++) {
						productIds.add(Integer.parseInt(idList[i]));
					}
					cartLists = dataAccess.getCartList(userId, productIds);
					request.setAttribute("selectAll", false);
				}
				request.setAttribute("cartLists", cartLists);
				return mapping.findForward("cart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<ProductForm> cartLists = dataAccess.getCartList(userId, null);
				request.setAttribute("selectAll", true);
				request.setAttribute("cartLists", cartLists);
				return mapping.findForward("cart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward buyNow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			Boolean productState = (Boolean) request.getAttribute("productState");
			if (id != null && userId != null && productState != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				if (productState == false) {
					dataAccess.addToCart(userId, productId, false);
				}
				ArrayList<Integer> productIds = new ArrayList<Integer>();
				productIds.add(productId);
				ArrayList<ProductForm> cartLists = dataAccess.getCartList(userId, productIds);
				request.setAttribute("selectAll", false);
				request.setAttribute("cartLists", cartLists);
				return mapping.findForward("cart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				String selectAll = request.getParameter("selectAll");
				String[] idList = request.getParameterValues("idList");
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				if(selectAll!=null) {
					dataAccess.changeProductStatus(userId);	
				}else if(idList!=null) {
					ArrayList<Integer> productIds=new ArrayList<Integer>();
					for(int i=0;i<idList.length;i++) {
						productIds.add(Integer.parseInt(idList[i]));
					}
					dataAccess.changeProductStatus(userId, productIds);
				}
				ArrayList<ProductForm> purchaseLists = dataAccess.getPurchaseList(userId);
				request.setAttribute("purchaseLists", purchaseLists);
				ArrayList<UserForm> addressList = dataAccess.getAddressList(userId);
				request.setAttribute("addressList", addressList);
				return mapping.findForward("purchase");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}
	}

	public ActionForward addAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			UserForm userForm = (UserForm) form;
			String address = userForm.getAddress();
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null && address != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addAddress(userId, address);
				request.getSession().setAttribute("message", "Address added successfully");
				return mapping.findForward("viewPurchase");
			} else {
				return mapping.findForward("success");
			}
		} else {

			return mapping.findForward("success");
		}
	}

	public ActionForward makePurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			UserForm userForm = (UserForm) form;
			Integer addressId = userForm.getAddressId();
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (addressId != null && userId != null) {
				// push data to the productOrder Table
				// Remove details from the cart table
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.makePurchase(userId, addressId);
				request.getSession().setAttribute("message", "Order Placed Successfully");
				return mapping.findForward("viewOrders");
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}
	}

	public ActionForward viewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<UserForm> orderLists = dataAccess.getOrderList(userId);
				request.setAttribute("orderLists", orderLists);
				return mapping.findForward("orders");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward cancelItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id1 = request.getParameter("orderId");
			String id2 = request.getParameter("productId");
			String q = request.getParameter("quantity");
			if (id1 != null && id2 != null && q != null) {
				Integer orderId = Integer.parseInt(id1);
				Integer productId = Integer.parseInt(id2);
				Integer quantity = Integer.parseInt(q);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.cancelItem(orderId, productId, quantity);
				request.getSession().setAttribute("message", "Product Cancellation is successful");
				return mapping.findForward("viewOrders");
			} else
				return mapping.findForward("success");
		} else {
			return mapping.findForward("success");
		}

	}

	public boolean validate(HttpServletRequest request) {
		if (request.getSession().getAttribute("role") == null)
			return false;
		else if ("admin".equals(request.getSession().getAttribute("role")))
			return false;
		return true;
	}
}
