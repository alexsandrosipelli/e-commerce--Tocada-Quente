package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /*listar os produtos*/
    @GetMapping("/produtos")
    public String listarProdutos(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword, Principal principal) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        String tipoUsuario = usuarioLogado.getGrupo();

        model.addAttribute("tipoUsuario", tipoUsuario);

        Page<ProdutoDTO> produtosPage = produtoService.listarProdutosPorNomePaginado(keyword, PageRequest.of(page, 10, Sort.by("id").descending()));
        model.addAttribute("produtosPage", produtosPage);
        model.addAttribute("keyword", keyword);
        return "listar-Produtos";
    }

    /*acessar a pagina de criaçao de produto*/
    @GetMapping("/produtos/novo")
    public String mostrarFormularioCriacaoProduto(Model model) {
        model.addAttribute("produtoDTO", new ProdutoDTO());
        return "criarproduto";
    }

    /*mandando a solicçao post para criar o produto*/
    @PostMapping("/criarproduto")
    public String criarProduto(@Valid @ModelAttribute("produtoDTO") ProdutoDTO produtoDTO,
            BindingResult result,
            @RequestParam("imagens") List<MultipartFile> imagens) throws IOException {
        if (result.hasErrors()) {

            System.out.println("teve erros" + result.getAllErrors());
            return "criarproduto";
        }

        produtoService.criarProduto(produtoDTO, imagens); // Handle file processing errors

        return "redirect:/produtos";
    }

    /*Alterar status produto*/
    @PostMapping("/produtos/mudar-status") // Mapeado para requisições POST
    public String mudarStatusProduto(@RequestParam Long id, RedirectAttributes attributes) {
        try {
            // Chama o método da service para mudar o status do produto
            produtoService.mudarStatusProduto(id);
            attributes.addFlashAttribute("mensagem", "Produto alterado com sucesso!");
        } catch (EntityNotFoundException ex) {
            attributes.addFlashAttribute("mensagem", "Produto não encontrado!");
        }
        return "redirect:/produtos";
    }
}
