package com.duc.project.controller.admin;

import com.duc.project.model.Category;
import com.duc.project.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    public String index(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Category> list = this.categoryService.getAll(pageNo);
        if(keyword != null) {
            list = this.categoryService.searchCategory(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("list", list);
        return "admin/category/index";
    }

    @GetMapping("/add-category")
    public String add(Model model) {
        Category category = new Category();
        category.setCategoryStatus(true);
        model.addAttribute("category", category);
        return "admin/category/add";
    }
    @PostMapping("/add-category")
    public String save(@ModelAttribute("category") Category category) {
        if(this.categoryService.create(category)) {
            return "redirect:/admin/category";
        }
        return "admin/category/add";
    }
    @GetMapping("/edit-category/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }
    @PostMapping("/edit-category")
    public String update(@ModelAttribute("category") Category category) {
        if(this.categoryService.create(category)) {
            return "redirect:/admin/category";
        }
        return "admin/category/add";
    }
    @GetMapping("/delete-category/{id}")
    public String delete(@PathVariable("id") Integer id, HttpSession session) {
        if(this.categoryService.delete(id)) {
            session.setAttribute("msg", "Xóa thành công");
            return "redirect:/admin/category";
        }
        session.setAttribute("msg", "Xóa thất bại");
        return "redirect:/admin/category";
    }
}