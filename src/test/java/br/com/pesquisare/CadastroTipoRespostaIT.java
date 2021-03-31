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

import br.com.pesquisare.domain.model.TipoResposta;
import br.com.pesquisare.domain.repository.TipoRespostaRepository;
import br.com.pesquisare.util.DatabaseCleaner;
import br.com.pesquisare.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroTipoRespostaIT {

	private static final int TIPO_RESPOSTA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private TipoRespostaRepository tipoRespostaRepository;
	
	private TipoResposta tipoRespostaHum;
	private int quantidadeTiposRespostasCadastradas;
	private String jsonCorretoTipoRespostaNova;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/tipos-respostas";

		jsonCorretoTipoRespostaNova = ResourceUtils.getContentFromResource(
				"/json/correto/tipo-resposta-nova.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarTiposRespostas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDePesquisas_QuandoConsultarTiposRespostas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeTiposRespostasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarTipoResposta() {
		given()
			.body(jsonCorretoTipoRespostaNova)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarTipoRespostaExistente() {
		given()
			.pathParam("tipoRespostaId", tipoRespostaHum.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{tipoRespostaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("descricao", equalTo(tipoRespostaHum.getDescricao()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarTipoRespostaInexistente() {
		given()
			.pathParam("tipoRespostaId", TIPO_RESPOSTA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{tipoRespostaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		
		tipoRespostaHum = new TipoResposta();
		tipoRespostaHum.setDescricao("Somente uma alternativa correta");
		tipoRespostaRepository.save(tipoRespostaHum);
		
		TipoResposta tipoPesquisaNova = new TipoResposta();
		tipoPesquisaNova.setDescricao("Sim ou Não");
		tipoRespostaRepository.save(tipoPesquisaNova);
		
		quantidadeTiposRespostasCadastradas = (int) tipoRespostaRepository.count();
	}

}
