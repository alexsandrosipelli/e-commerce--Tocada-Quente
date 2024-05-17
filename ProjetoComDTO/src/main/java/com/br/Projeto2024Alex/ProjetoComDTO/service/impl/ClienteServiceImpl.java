/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoEntregaEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoFaturamentoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.EnderecoEntregaRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.EnderecoFaturamentoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexs
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final PasswordEncoder encoder;
    private final ClienteRepository clienteRepository;
    private final EnderecoServiceImpl enderecoServiceImpl;
    private final EnderecoFaturamentoRepository enderecoFaturamentoRepository;
    private final EnderecoEntregaRepository enderecoEntregaRepository;

    @Autowired
    public ClienteServiceImpl(PasswordEncoder encoder, ClienteRepository repository, EnderecoServiceImpl enderecoServiceImpl,
                              EnderecoFaturamentoRepository enderecoFaturamentoRepository, EnderecoEntregaRepository enderecoEntregaRepository) {
        this.encoder = encoder;
        this.clienteRepository = repository;
        this.enderecoServiceImpl = enderecoServiceImpl;
        this.enderecoFaturamentoRepository = enderecoFaturamentoRepository;
        this.enderecoEntregaRepository = enderecoEntregaRepository;
    }

    @Override
    public String salvarCliente(ClienteDTO clienteDto, BindingResult result, RedirectAttributes attributes) {
        //TODO FALTA FAZER O CEP SER CONSULTADO NA API EXTERNA E PREENCHER OS CAMPOS COM O RESULTADO.
        ModelMapper modelMapper = new ModelMapper();

        boolean temErros = validacaoCamposCadastro(clienteDto, result);

        if (temErros){
            return "criar-cliente";
        }else{
            clienteDto = preencherCampos(clienteDto);
            ClienteEntity clienteEntity = modelMapper.map(clienteDto, ClienteEntity.class);
            clienteRepository.save(clienteEntity);
            salvarEnderecos(clienteDto);
            return "redirect:/cliente/";
        }
    }

    @Override
    public void salvarEnderecos(ClienteDTO clienteDto) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteEntity clienteEntity = clienteRepository.findByEmail(clienteDto.getEmail());
        clienteDto = consultarCep(clienteEntity);
        List<EnderecoFaturamentoDTO> listaEnderecoFaturamentoDto = clienteDto.getEnderecoFaturamentoDto();
        List<EnderecoEntregaDTO> listaEnderecoEntregaDto = clienteDto.getEnderecoEntregaDto();

        for (EnderecoFaturamentoDTO valores: listaEnderecoFaturamentoDto){
            EnderecoFaturamentoEntity enderecoFaturamentoEntity = modelMapper.map(valores, EnderecoFaturamentoEntity.class);
            enderecoFaturamentoRepository.save(enderecoFaturamentoEntity);
        }

        for (EnderecoEntregaDTO valores: listaEnderecoEntregaDto){
            EnderecoEntregaEntity enderecoEntregaEntity = modelMapper.map(valores, EnderecoEntregaEntity.class);
            enderecoEntregaRepository.save(enderecoEntregaEntity);
        }
    }

    private ClienteDTO preencherCampos(ClienteDTO clienteDto) {
        clienteDto.setDataNascimento(clienteDto.getDataNascimento().replace("-", ""));
        String senhaCriptografada = encoder.encode(clienteDto.getSenha());
        clienteDto.setSenha(senhaCriptografada);
        return clienteDto;
    }

    private boolean validacaoCamposCadastro(ClienteDTO clienteDTO, BindingResult result) {
        if (clienteRepository.existsByEmailIgnoreCase(clienteDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            result.rejectValue("cpf", "email.cpf", "O cpf já está cadastrado. Por favor, escolha outro.");
        }

        return result.hasErrors();
    }

    @Override
    public ClienteDTO consultarCep(ClienteEntity clienteEntity) {
        ModelMapper modelMapper = new ModelMapper();
        EnderecoViacepDTO viacepDTO = new EnderecoViacepDTO();
        List<EnderecoEntregaDTO> listaEnderecoEntrega = new ArrayList<>();
        List<EnderecoFaturamentoDTO> listaEnderecoFaturamento = new ArrayList<>();
        ClienteDTO clienteDTO = modelMapper.map(clienteEntity, ClienteDTO.class);

        viacepDTO.setCep(clienteEntity.getCep());

        EnderecoEntregaDTO enderecoEntregaDTO = enderecoServiceImpl.executaBuscaEnderecoEntrega(viacepDTO);
        EnderecoFaturamentoDTO enderecoFaturamentoDto = enderecoServiceImpl.executaBuscaEnderecoFaturamento(viacepDTO);

        enderecoEntregaDTO.setCliente(clienteDTO);
        enderecoFaturamentoDto.setCliente(clienteDTO);

        listaEnderecoEntrega.add(enderecoEntregaDTO);
        listaEnderecoFaturamento.add(enderecoFaturamentoDto);

        clienteDTO.setLocalidade(enderecoEntregaDTO.getLocalidade());
        clienteDTO.setUf(enderecoEntregaDTO.getUf());
        clienteDTO.setEnderecoEntregaDto(listaEnderecoEntrega);
        clienteDTO.setEnderecoFaturamentoDto(listaEnderecoFaturamento);

        return clienteDTO;
    }
}
