package com.caixa.planilha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.caixa.planilha.DAO.CaixaDAO;
import com.caixa.planilha.Enum.Status;
import com.caixa.planilha.models.Caixa;

import java.util.List;

@Controller
public class HomeController {

  private final CaixaDAO caixaDAO;

  @Autowired
  public HomeController(CaixaDAO caixaDAO) {
    this.caixaDAO = caixaDAO;
  }

    @GetMapping("/")
    public String index(Model model) {

      List<Caixa> caixas = caixaDAO.findAll();

      float totalReceitas = 0;
      float totalDespesas = 0;

      for (Caixa caixa : caixas) {
        if (caixa.getStatus().equals(Status.RECEITA)) {
          totalReceitas += caixa.getValor();
        } else if (caixa.getStatus().equals(Status.DESPESA)) {
          totalDespesas += caixa.getValor();
        }
      }

      float valorTotal = totalReceitas - totalDespesas;

      model.addAttribute("caixas", caixas);
      model.addAttribute("totalReceitas", totalReceitas);
      model.addAttribute("totalDespesas", totalDespesas);
      model.addAttribute("valorTotal", valorTotal);

      return "home/index";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id) {
      if (caixaDAO.existsById(id)) {
        caixaDAO.deleteById(id);
      } else {
        System.out.println("Registro com ID" + id + "não encontrado");
      }
      return "redirect:/";
    }
}
