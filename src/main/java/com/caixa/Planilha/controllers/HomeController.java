package com.caixa.planilha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caixa.planilha.DAO.CaixaDAO;
import com.caixa.planilha.Enum.Status;
import com.caixa.planilha.models.Caixa;

import java.text.DecimalFormat;
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

      DecimalFormat df = new DecimalFormat("#,##0.00");
      String valorTotalFormatado = df.format(valorTotal);
      String totalReceitasFormatado = df.format(totalReceitas);
      String totalDespesasFormatado = df.format(totalDespesas);

      model.addAttribute("caixas", caixas);
      model.addAttribute("totalReceitas", totalReceitasFormatado);
      model.addAttribute("totalDespesas", totalDespesasFormatado);
      model.addAttribute("valorTotal", valorTotalFormatado);

      return "home/index";
    }

    @GetMapping("/adicionar")
    public String adicionar() {
      return "home/adicionar";
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

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam("tipo") String tipo,
                            @RequestParam("valor") float valor,
                            @RequestParam("status") int status,
                            Model model) {
        Caixa caixa = new Caixa();
        caixa.setTipo(tipo);
        caixa.setValor(valor);
        Status statusEnum = Status.values()[status];
        caixa.setStatus(statusEnum);

        caixaDAO.save(caixa); // Salva no banco de dados

        return "redirect:/"; // Redireciona para a página de listagem
    }
}
