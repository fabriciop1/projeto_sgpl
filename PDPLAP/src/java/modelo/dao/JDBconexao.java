package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBconexao {

	//atributos para conex?o
	private String url;
	private String usuario;
	private String senha;
	private String db;
	private String porta;
	private String driver;
	private String servername;
	private String escopo;
	
	//atributos de retorno do banco
	private ResultSet retorno;
	private Statement confirmacao;
	private Connection conexao; //armazena a conexao real com o banco
	
	
	//m?todo conetar
	public void conectar() throws ClassNotFoundException, SQLException
	{
		//setando os valores
		setarValores();
		
		Class.forName(getDriver());
		
		//setando os valores da url para conexao
		setUrl(getEscopo()+getServername()+":"+getPorta()+"/"+getDb());
		
		//estabelecendo a conex?o
		setConexao(DriverManager.getConnection(getUrl(),getUsuario(),getSenha()));
	}	
	
	public void setarValores()
	{
		setServername("localhost");
		setUsuario("root");
		setSenha("foreign");
		setDb("projetopesquisaleite");
		setPorta("3306");
		setDriver("com.mysql.jdbc.Driver");
		setEscopo("jdbc:mysql://");
	}
	
	

	//m?todos get e set
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/**
	 * @return the db
	 */
	public String getDb() {
		return db;
	}
	/**
	 * @param db the db to set
	 */
	public void setDb(String db) {
		this.db = db;
	}
	/**
	 * @return the porta
	 */
	public String getPorta() {
		return porta;
	}
	/**
	 * @param porta the porta to set
	 */
	public void setPorta(String porta) {
		this.porta = porta;
	}
	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * @return the servername
	 */
	public String getServername() {
		return servername;
	}
	/**
	 * @param servername the servername to set
	 */
	public void setServername(String servername) {
		this.servername = servername;
	}
	/**
	 * @return the escopo
	 */
	public String getEscopo() {
		return escopo;
	}
	/**
	 * @param escopo the escopo to set
	 */
	public void setEscopo(String escopo) {
		this.escopo = escopo;
	}



	/**
	 * @return the retorno
	 */
	public ResultSet getRetorno() {
		return retorno;
	}


	/**
	 * @param retorno the retorno to set
	 */
	public void setRetorno(ResultSet retorno) {
		this.retorno = retorno;
	}


	/**
	 * @return the confirmacao
	 */
	public Statement getConfirmacao() {
		return confirmacao;
	}


	/**
	 * @param confirmacao the confirmacao to set
	 */
	public void setConfirmacao(Statement confirmacao) {
		this.confirmacao = confirmacao;
	}



	/**
	 * @return the conexao
	 */
	public Connection getConexao() {
		return conexao;
	}


	/**
	 * @param conexao the conexao to set
	 */
	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	

}
