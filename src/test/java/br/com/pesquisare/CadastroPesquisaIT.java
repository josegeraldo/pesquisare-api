package br.com.pesquisare;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.pesquisare.domain.model.Pesquisa;
import br.com.pesquisare.domain.repository.PesquisaRepository;
import br.com.pesquisare.util.DatabaseCleaner;
import br.com.pesquisare.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroPesquisaIT {

	private static final int PESQUISA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private PesquisaRepository pesquisaRepository;
	
	private Pesquisa pesquisaUm;
	private int quantidadePesquisasCadastradas;
	private String jsonCorretoPesquisaNova;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		jsonCorretoPesquisaNova = ResourceUtils.getContentFromResource(
				"/json/correto/pesquisa-nova.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarPesquisas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDePesquisas_QuandoConsultarPesquisas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadePesquisasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarPesquisa() {
		given()
			.body(jsonCorretoPesquisaNova)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarPesquisaExistente() {
		given()
			.pathParam("pesquisaId", pesquisaUm.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{pesquisaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(pesquisaUm.getDescricao()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarPesquisaInexistente() {
		given()
			.pathParam("cozinhaId", PESQUISA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		
		pesquisaUm = new Pesquisa();
		pesquisaUm.setAtivo(true);
		pesquisaUm.setDescricao("PesquisaUm");
		pesquisaUm.setTitulo("Titulo Um");
		pesquisaRepository.save(pesquisaUm);
		
		Pesquisa pesquisaNova = new Pesquisa();
		pesquisaNova.setAtivo(true);
		pesquisaNova.setDescricao("PesquisaNova");
		pesquisaNova.setTitulo("Titulo Novo");
		pesquisaRepository.save(pesquisaNova);
		
		quantidadePesquisasCadastradas = (int) pesquisaRepository.count();
	}

}
