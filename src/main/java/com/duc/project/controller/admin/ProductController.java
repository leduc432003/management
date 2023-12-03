package com.duc.project.controller.admin;

import com.duc.project.model.Category;
import com.duc.project.model.Product;
import com.duc.project.service.CategoryService;
import com.duc.project.service.ProductService;
import com.duc.project.service.StorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ProductService productService;
    @RequestMapping("/product")
    public String index(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> list = this.productService.getAll(pageNo);
        if(keyword != null) {
            list = this.productService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listProduct", list);
        return "admin/product/index";
    }
    @RequestMapping("/product-add")
    public String add(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> listCategories = this.categoryService.getAll();
        model.addAttribute("listCate", listCategories);
        return "admin/product/add";
    }
    @PostMapping("/product-add")
    public String save(@ModelAttribute("product") Product product, @RequestParam("fileImage")MultipartFile file) {
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        if(this.productService.create(product)) {
            return "redirect:/admin/product";
        }
        return "admin/product/add";
    }
    @GetMapping("/edit-product/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Product product = this.productService.findById(id);
        model.addAttribute("product", product);
        List<Category> listCategories = this.categoryService.getAll();
        model.addAttribute("listCate", listCategories);
        return "admin/product/edit";
    }
    @PostMapping("/edit-product")
    public String update(@ModelAttribute("product") Product product, @RequestParam("fileImage")MultipartFile file) {
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        if(this.productService.create(product)) {
            return "redirect:/admin/product";
        }
        return "admin/product/add";
    }
    @GetMapping("/delete-product/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if(this.productService.delete(id)) {
            return "redirect:/admin/product";
        }
        return "redirect:/admin/product";
    }
}
