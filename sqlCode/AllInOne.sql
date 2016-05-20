
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE projeto_pesquisa.dados_economicos_mensais;
TRUNCATE TABLE projeto_pesquisa.dem_especificacao;
TRUNCATE TABLE projeto_pesquisa.inventario_animais;
TRUNCATE TABLE projeto_pesquisa.inventario_benfeitorias;
TRUNCATE TABLE projeto_pesquisa.inventario_maquinas;
TRUNCATE TABLE projeto_pesquisa.inventario_resumo;
TRUNCATE TABLE projeto_pesquisa.inventario_terras;
TRUNCATE TABLE projeto_pesquisa.inventario_forrageiras;
TRUNCATE TABLE projeto_pesquisa.perfil;
TRUNCATE TABLE projeto_pesquisa.rota;
TRUNCATE TABLE projeto_pesquisa.usuario;
TRUNCATE TABLE projeto_pesquisa.dtm_indicador;
TRUNCATE TABLE projeto_pesquisa.dados_tecnicos_mensais;
SET FOREIGN_KEY_CHECKS = 1;

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.rota (rota) VALUES ("São Bento Curta");
INSERT INTO projeto_pesquisa.rota (rota) VALUES ("São Bento Longa");
INSERT INTO projeto_pesquisa.rota (rota) VALUES ("Lajedo");
INSERT INTO projeto_pesquisa.rota (rota) VALUES ("Miracica");

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("adriana", "adriana", 1);
INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("diniz", "diniz", 2);
INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("adm", "adm", 1);
INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("adm", "adm", 2);
INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("adm", "adm", 3);
INSERT INTO projeto_pesquisa.usuario (login, senha, idRotaFK) VALUES ("adm", "adm", 4);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.perfil (nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, 
	empPermanentes, numFamiliares, idRotaFK) VALUES ("Adriana Veloso", 		 "São Bento do Una", 8.8, 	8.8,  60, 0, 2, 1);
INSERT INTO projeto_pesquisa.perfil (nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, 
	empPermanentes, numFamiliares, idRotaFK) VALUES ("Diniz Souza dos Santos", "São Bento do Una", 30.8, 19.5, 465, 0, 2, 2);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.inventario_terras (especificacao, areaArrendadaInicio, areaPropriaInicio, 
		areaArrendadaFinal, areaPropriaFinal, valorTerraNuaPropria, idPerfilFK)
        VALUES ("Pastagem Nativa", 	0, 2.8, 0, 	0, 10000,  1);
INSERT INTO projeto_pesquisa.inventario_terras (especificacao, areaArrendadaInicio, areaPropriaInicio, 
		areaArrendadaFinal, areaPropriaFinal, valorTerraNuaPropria, idPerfilFK)
        VALUES ("Pastagem Pangola", 	0, 3.0, 0, 	0, 10000, 1);
INSERT INTO projeto_pesquisa.inventario_terras (especificacao, areaArrendadaInicio, areaPropriaInicio, 
		areaArrendadaFinal, areaPropriaFinal, valorTerraNuaPropria, idPerfilFK)
        VALUES ("Palma",				0, 3.0, 0, 	0, 10000, 1);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Picadeira de Forragens",  "Ud", 1.0, 3000, 10, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Ordenhadeira Mecânica",   "Ud", 0.0, 5000, 10, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Pulverizador", 		    "Ud", 0.0, 100,  8,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Arado", 				    "Ud", 0.0, 3500, 10, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Carreta", 				"Ud", 0.0, 2300, 12, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Moto", 				    "Ud", 0.0, 7000, 7,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Adubadeira Tração Animal", "Ud", 0.0, 2500, 5,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Plaina Traseira", 		    "Ud", 0.0, 3000, 10, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Batedor de Cereais", 	    "Ud", 0.0, 3000, 10, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Carroça", 				    "Ud", 2.0, 700,  8,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Misturador", 			    "Ud", 0.0, 1600, 20, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Desintegrador",      	    "Ud", 0.0, 2000, 15, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Arado Tração Animal", 		"Ud", 0.0, 500,  15, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Bomba de Água", 		    "Ud", 0.0, 500,  6,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Eletrificador", 		    "Ud", 0.0, 60, 	 5,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Caixa de Água", 		    "Ud", 0.0, 1500, 20, 1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Roçadeira", 			    "Ud", 0.0, 700,  5,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Computador", 			    "Ud", 0.0, 3500, 5,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Botijão de Sêmen", 	    "Ud", 1.0, 200,  8,  1);
INSERT INTO projeto_pesquisa.inventario_maquinas  (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)
	VALUES ("Outros", 				    "Ud", 0.0, 2000, 20, 1);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.inventario_forrageiras (forrageirasNaoAnuais, custoFormacaoHectare, vidaUtil, idPerfilFK, idInventarioTerrasFK)
	VALUES ("Pastagem Nativa", 0.0, 1, 1, 1);
INSERT INTO projeto_pesquisa.inventario_forrageiras (forrageirasNaoAnuais, custoFormacaoHectare, vidaUtil, idPerfilFK, idInventarioTerrasFK)
	VALUES ("Pastagem Pangola", 1000, 6, 1, 2);
INSERT INTO projeto_pesquisa.inventario_forrageiras (forrageirasNaoAnuais, custoFormacaoHectare, vidaUtil, idPerfilFK, idInventarioTerrasFK)
	VALUES ("Palma", 3000, 10, 1, 3);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Sala de Ordenha", 		"Ud", 1.0, 	1600, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Currais de Manejo", 	"Ud", 1.0, 	500, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Casa Sede", 			"Ud", 1.0, 	18000, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Cercas Convencional", 	"Km", 25.4, 3000, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Depósito de Ração", 	"Ud", 1.0, 	1000, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Cochos", 				"Ud", 1.0, 	300, 	10, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) 
	VALUES ("Açude", 				"Ud", 1.0, 	2500, 	20, 1);
INSERT INTO projeto_pesquisa.inventario_benfeitorias (especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK)  
	VALUES ("Armazém", 				"R$", 0.0, 	0.0, 	1, 	1);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Touro", 			0, 0, 0, 0, 0, 0, 0, 	1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Vaca Parida", 		7, 0, 0, 0, 0, 0, 1500, 1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Vaca Falhada", 		2, 0, 0, 0, 0, 0, 1500, 1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Machos 0-1 ano", 	0, 0, 0, 0, 0, 0, 0, 	1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Fêmeas 0-1 ano", 	4, 0, 0, 0, 0, 0, 400,  1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Fêmeas 1-2 anos", 	3, 0, 0, 0, 0, 0, 600, 	1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Fêmeas 2-3 anos", 	4, 0, 0, 0, 0, 0, 800, 	1, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Equinos", 			0, 0, 0, 0, 0, 0, 0, 	2, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Muares", 			2, 0, 0, 0, 0, 0, 200, 	2, 1);
INSERT INTO projeto_pesquisa.inventario_animais VALUES (0, "Boi de carro", 		0, 0, 0, 0, 0, 0, 0, 	2, 1);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Produção Total de Leite");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Venda de Leite (L)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Venda de Animais(und)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Empréstimos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Queijo");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Outros(Entrada)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Mão de obra permanente");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Mão de obra temporária");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Energia");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Combustível");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Consumo Interno(produtor e func.)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Leite para Bezerro");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Impostos e Taxas");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Reparos de Benfeitorias");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Reparos de Máquinas");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Outros(Saída)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Gastos Extras");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Compra de volumoso (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Compra de volumoso (Kg)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Calcário (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Adubo químico (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Adubo orgânico comprado (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Serviços mecânicos (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Serviços de tração animal (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Defensivo/veneno (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Frete (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Sementes (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Aluguel de pastagens (R$)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Concentrado rebanho (cria)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Concentrado rebanho (produção)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Fubá de milho");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Farelo de trigo");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Farelo de algodão");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Farelo de soja");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Melaço");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Uréia");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Cama de galinha");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Outros(Concentrado)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Sal comum");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Sal mineral");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Concentrado mineral");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Farinha de ossos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Fosfato bicálcico");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Mistura preparada na fazenda");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vermífugos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Carrapaticida");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Mata-bicheira");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vacina (aftosa)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vacina (brucelose)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vacina (manqueira)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vacina (raiva)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vacina (outras)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Antibióticos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Antitóxicos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Material de limpeza e desinfecção");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Vitaminas e anti-inflamatório");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Detergentes");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Desinfetantes para tetos");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Materiais");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Outros(Ordenha)");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Sêmen");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Nitrogênio líquido");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Luvas");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Pipetas/bainha");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Inseminador contratado");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Formação de pastagens/Forragem de corte");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Benfeitorias/Instalações/Máquinas");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Aquisição de animais");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Pagamento de empréstimos no mês");
INSERT INTO projeto_pesquisa.dem_especificacao (especificacao) VALUES ("Mão-de-obra familiar (não paga)");

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 1, 1600, 1, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 0, 0.0, 2, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 0, 0.0, 3, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 0, 0.0, 4, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 0, 0.0, 5, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (1, 2015, 0, 0.0, 6, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 0, 0.0, 1, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 1476, 0.85, 2, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 4, 30.0, 7, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 1, 15.56, 9, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 1, 23.80, 10, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 4, 28.0, 31, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 8, 22.0, 32, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 4, 45.0, 33, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 4, 70.0, 34, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario, 
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 10, 9.0, 37, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario, 
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 1, 35.0, 40, 1);
INSERT INTO projeto_pesquisa.dados_economicos_mensais (mes, ano, quantidade, valorUnitario,
				idDEM_especificacaoFK, idPerfilFK) VALUES (2, 2015, 1, 15.50, 60, 1);

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº V. Lactação");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº V. Secas ");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Novilhas");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Bezerras");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Bezerros");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Touros");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Outros");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Coberturas");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Partos");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Animais Comprados");

INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Abortos");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Natimortos");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Retenção de Placenta");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Mortes de Bezerros");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Bezerros Doentes");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Mortes de Novilhas");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Mortes de Vacas");
INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Nº de Vacas com Mastite Clínica");

INSERT INTO projeto_pesquisa.dtm_indicador (indicador) VALUES ("Total Litros/Mês (L)");

/*===============================================================================================================================*/

INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,7,1,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,1,2,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,7,3,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,4,5,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,1,6,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,1,9,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (1,2015,1662,19,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (7,2015,7,1,1);
INSERT INTO projeto_pesquisa.dados_tecnicos_mensais (mes, ano, dado, idDTM_indicadorFK, idPerfilFK) 
		VALUES (7,2015,1476,19,1);
        
/*===============================================================================================================================*/

