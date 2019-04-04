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

public class AdminAction extends DispatchAction {
	public ActionForward home(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			return mapping.findForward("admin");
		} else
			return mapping.findForward("success");
	}

	public ActionForward addProductPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			return mapping.findForward("addProduct");
		} else
			return mapping.findForward("success");

	}

	public ActionForward addProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			ProductForm productForm = (ProductForm) form;
			if (productForm.getProductName() != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addProduct(productForm);
				request.getSession().setAttribute("message", "Product added successfully");
				return mapping.findForward("edit");
			} else {
				return mapping.findForward("addProduct");
			}
		} else
			return mapping.findForward("success");

	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			ShoppingCartDA dataAccess = new ShoppingCartDA();
			ArrayList<ProductForm> productList = dataAccess.getProduct();
			request.setAttribute("productList", productList);
			return mapping.findForward("editProduct");
		} else
			return mapping.findForward("success");

	}

	public ActionForward updateProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			ProductForm productForm = (ProductForm) form;
			if (productForm.getProductPrice() != null && id != null) {
				productForm.setProductId(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.updateProduct(productForm);
				request.getSession().setAttribute("message", "Product updated successfully");
				return mapping.findForward("edit");
			} else {
				return mapping.findForward("adminHome");
			}
		} else
			return mapping.findForward("success");

	}

	public ActionForward removeProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			if (id != null) {
				Integer productId=Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.disableProduct(productId);
				request.getSession().setAttribute("message", "Product is disabled");
				return mapping.findForward("edit");
			} else
				return mapping.findForward("adminHome");

		} else
			return mapping.findForward("success");

	}
	public ActionForward enableProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			if (id != null) {
				Integer productId=Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.enableProduct(productId);
				request.getSession().setAttribute("message", "Product is enabled");
				return mapping.findForward("edit");
			} else
				return mapping.findForward("adminHome");

		} else
			return mapping.findForward("success");

	}
//	public ActionForward viewOrderLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		if (validate(request)) {
//			return mapping.findForward("");
//		} else
//			return mapping.findForward("success");
//	}
	

	public boolean validate(HttpServletRequest request) {
		if (request.getSession().getAttribute("role") == null)
			return false;
		else if ("normal".equals(request.getSession().getAttribute("role")))
			return false;
		return true;
	}

}
