package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.controller.CarrinhoController;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.*;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.EnderecoEntregaRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ItemCompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    private ProdutoRepository produtoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrinhoController.class);
    private List<ItemCompraEntity> itemCompra = new ArrayList<>();
    private CompraEntity compra = new CompraEntity();
    private EnderecoEntregaRepository enderecoEntregaRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CarrinhoServiceImpl(ProdutoRepository produtoRepository, EnderecoEntregaRepository enderecoEntregaRepository, ModelMapper modelMapper) {
      this.produtoRepository = produtoRepository;
      this.enderecoEntregaRepository = enderecoEntregaRepository;
      this.modelMapper = modelMapper;
    }

    private void calcularTotal() {
        // Inicializa o valor total como BigDecimal zero.
        BigDecimal total = BigDecimal.ZERO;

        // Itera sobre os itens da compra.
        for (ItemCompraEntity it : itemCompra) {
            // Soma o valor total do item ao total.
            total = total.add(it.getValorTotal());
        }

        // Define o valor total na compra.
        compra.setValorTotal(total);
    }

    @Override
    public String adicionarItemCarrinho(Long id) {
        LOGGER.info("Chamando o método adicionarCarrinho() para o id: {}", id);

        Optional<ProdutoEntity> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            ProdutoEntity prod = produtoOpt.get();
            LOGGER.info("Produto encontrado: {}", prod);

            int controle = 0;
            for (ItemCompraEntity it : itemCompra) {
                if (it.getProdutoEntity().getId().equals(prod.getId())) {
                    LOGGER.info("Produto já existe no carrinho. Atualizando a quantidade.");
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.calcularValorTotal();
                    controle = 1;
                    break;
                }
            }

            if (controle == 0) {
                LOGGER.info("Produto não encontrado no carrinho. Adicionando um novo item.");
                ItemCompraEntity item = new ItemCompraEntity();
                item.setProdutoEntity(prod);
                item.setValorUnitario(prod.getPrecoProduto());
                item.setQuantidade(1); // Inicializa a quantidade como 1 para um novo item
                item.calcularValorTotal();
                itemCompra.add(item);
            }

        } else {
            LOGGER.error("Produto com id {} não encontrado.", id);
        }

        return "redirect:/site/carrinhoDeCompras/";
    }

    @Override
    public ModelAndView carrinhoExibir() {
        ModelAndView mv = new ModelAndView("carrinho");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itemCompra);
        return mv;
    }

    @Override
    public String editarQuantidade(Long id, Integer acao) {
        for (ItemCompraEntity it : itemCompra) {
            if (it.getProdutoEntity().getId().equals(id)) {
                if (acao == 1) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.calcularValorTotal();
                } else if (acao == 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.calcularValorTotal();
                }
                break;
            }
        }

        return "redirect:/site/carrinhoDeCompras/";
    }

    @Override
    public String removerProduto(Long id) {
        for (ItemCompraEntity it : itemCompra) {
            if (it.getProdutoEntity().getId().equals(id)) {
                itemCompra.remove(it);
                it.calcularValorTotal();
                break;
            }
        }
        return "redirect:/site/carrinhoDeCompras/";
    }

    @Override
    public String adicionarMetoPagamento(ModelAndView modelAndView, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            EnderecoEntregaEntity endereco = enderecoEntregaRepository.findByCliente_IdAndEnderecoPrincipalTrue(clienteLogado.getId());
            EnderecoEntregaDTO enderecoEntregaDTO = modelMapper.map(endereco, EnderecoEntregaDTO.class);
            compra.setCliente(clienteLogado);
            modelAndView.addObject("compra", compra);
            modelAndView.addObject("endereco", enderecoEntregaDTO);
            return "pagamento";
        }else{
            return "redirect:/site/cliente/";
        }
    }

    @Override
    public String finalizarCompra(String opcaoPagamento, Model model, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            EnderecoEntregaEntity endereco = enderecoEntregaRepository.findByCliente_IdAndEnderecoPrincipalTrue(clienteLogado.getId());
            EnderecoEntregaDTO enderecoEntregaDTO = modelMapper.map(endereco, EnderecoEntregaDTO.class);
            compra.setCliente(clienteLogado);
            compra.setFormaPagamento(opcaoPagamento);
            model.addAttribute("compra", compra);
            model.addAttribute("endereco", enderecoEntregaDTO);
            return "redirect:/site/carrinhoDeCompras/compra-finalizada";
        }else{
            return "redirect:/site/cliente/";
        }
    }

    @Override
    public String compraFinalizada(Model model, HttpSession session) {
        ClienteEntity clienteLogado = (ClienteEntity) session.getAttribute("clienteLogado");
        if (clienteLogado != null){
            return "nota-fiscal";
        }else{
            return "redirect:/site/cliente/";
        }
    }
}
