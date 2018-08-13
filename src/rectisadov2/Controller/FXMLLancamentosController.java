/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import rectisadov2.AppStart;
import rectisadov2.Controller.Cliente.FXMLAdicionarCompraController;
import rectisadov2.Controller.Cliente.FXMLEditarCompraController;
import rectisadov2.Controller.Fornecedor.FXMLAdicionarCompraFornecedorController;
import rectisadov2.model.NumberTableCellFactory;
import rectisadov2.model.Cliente;
import rectisadov2.model.Compras;
import rectisadov2.model.ECredito;
import rectisadov2.model.Gestor;
import rectisadov2.model.PDF.GerarPDF;
import rectisadov2.model.PDF.GerarPDFTodos;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLLancamentosController extends Stage {

    private AppStart appStart;
    private LocalDate dataGuardada;
    private LocalDate dataGuardadaFornecedores;
    private LocalDate dataGuardadaTodosFornecedoresInicio;
    private LocalDate dataGuardadaTodosFornecedoresFim;
    private LocalDate dataGuardadaTodosClientesInicio;
    private LocalDate dataGuardadaTodosClientesFim;
    private String ano;
    
    @FXML
    private TableView<Compras> tblClientes;
    
    @FXML
    private TableColumn<Compras, Integer> nrRegistoCliente;

    @FXML
    private TableColumn<Compras, LocalDate> dataCliente;

    @FXML
    private TableColumn<Compras, String> descricaoCliente;

    @FXML
    private TableColumn<Compras, ECredito> transacoesCliente;

    @FXML
    private TableColumn<Compras, Integer> requesicaoCliente;
    
    @FXML
    private TableColumn<Compras, Double> valorCliente;
    
    @FXML
    private TableView<Compras> tblFornecedores;

    @FXML
    private TableColumn<Compras, Integer> nComprasFornecedor;

    @FXML
    private TableColumn<Compras, LocalDate> dataFornecedor;

    @FXML
    private TableColumn<Compras, String> descricaoFornecedor;

    @FXML
    private TableColumn<Compras, ECredito> transacaoFornecedor;

    @FXML
    private TableColumn<Compras, Integer> requesicaoFornecedor;

    @FXML
    private TableColumn<Compras, Double> valorFornecedor;
    
    @FXML
    private DatePicker txtDt1;

    @FXML
    private DatePicker txtDt2;
    
    @FXML
    private DatePicker txtDtFornecedores1;

    @FXML
    private DatePicker txtDtFornecedores2;
    
    @FXML
    private TableView<Cliente> tblTodosClientes;

    @FXML
    private TableColumn<Cliente, String> ClienteTodosClientes;

    @FXML
    private TableColumn<Cliente, Double> debitoTodosClientes;

    @FXML
    private TableColumn<Cliente, Double> creditoTodosClientes;

    @FXML
    private TableColumn<Cliente, Double> saldoTodosClientes;

    @FXML
    private DatePicker txtDtTodosClientes1;

    @FXML
    private DatePicker txtDtTodosClientes2;

    @FXML
    private TableView<Cliente> tblTodosFornecedores;
    
    @FXML
    private TableColumn<Cliente, String> FornecedorTodosFornecedores;

    @FXML
    private TableColumn<Cliente, Double> debitoTodosFornecedores;

    @FXML
    private TableColumn<Cliente, Double> creditoTodosFornecedores;

    @FXML
    private TableColumn<Cliente, Double> saldoTodosFornecedores;

    @FXML
    private DatePicker txtDtTodosFornecedores1;

    @FXML
    private DatePicker txtDtTodosFornecedores2;
    
    @FXML
    private Label lblSaldoCliente;
    
    @FXML
    private Label lblSaldoFornecedor;
    
    public FXMLLancamentosController(AppStart appStart) {
        this.appStart = appStart;
        ano = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        dataGuardadaTodosClientesInicio = null;
        dataGuardadaTodosClientesFim = null;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLLancamentos.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
           
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        /********************************************************************
         * 
         * Cria os titulos da tableview do cliente e associa
         * á variavel em questão
         * 
         */
        nrRegistoCliente.setCellFactory(new NumberTableCellFactory<>(1));
        dataCliente.setCellValueFactory(new PropertyValueFactory<>("data"));
        dataCliente.setSortType(TableColumn.SortType.DESCENDING);
        descricaoCliente.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        transacoesCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCredito"));
        requesicaoCliente.setCellValueFactory(new PropertyValueFactory<>("requesicao"));
        valorCliente.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tblClientes.getSortOrder().add(dataCliente);
        /********************************************************************
         * 
         * Cria os titulos da tableview do fornecedor e associa
         * á variavel em questão
         * 
         */
        nComprasFornecedor.setCellFactory(new NumberTableCellFactory<>(1));
        dataFornecedor.setCellValueFactory(new PropertyValueFactory<>("data"));
        dataFornecedor.setSortType(TableColumn.SortType.DESCENDING);
        descricaoFornecedor.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        transacaoFornecedor.setCellValueFactory(new PropertyValueFactory<>("tipoCredito"));
        requesicaoFornecedor.setCellValueFactory(new PropertyValueFactory<>("requesicao"));
        valorFornecedor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tblFornecedores.getSortOrder().add(dataFornecedor);
        
        /********************************************************************
         * 
         * Cria os titulos da tableview de todos os clientes e associa
         * á variavel em questão
         * 
         */
        ClienteTodosClientes.setCellValueFactory(new PropertyValueFactory<>("nome"));
        debitoTodosClientes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().totalDebito("clientes", dataGuardadaTodosClientesInicio, dataGuardadaTodosClientesFim));
            }
        });
        creditoTodosClientes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().totalCredito("clientes", dataGuardadaTodosClientesInicio, dataGuardadaTodosClientesFim));
            }
        });
        saldoTodosClientes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().SaldoCliente("clientes", dataGuardadaTodosClientesInicio, dataGuardadaTodosClientesFim));
            }
        });
        tblTodosClientes.setItems(FXCollections.observableArrayList(Gestor.getInstance().todosClientes()));
        /********************************************************************
         * 
         * Cria os titulos da tableview de todos os fornecedores e associa
         * á variavel em questão
         * 
         */
        FornecedorTodosFornecedores.setCellValueFactory(new PropertyValueFactory<>("nome"));
        debitoTodosFornecedores.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().totalDebito("fornecedores", dataGuardadaTodosFornecedoresInicio, dataGuardadaTodosFornecedoresFim));
            }
        });
        creditoTodosFornecedores.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().totalCredito("fornecedores", dataGuardadaTodosFornecedoresInicio, dataGuardadaTodosFornecedoresFim));
            }
        });
        saldoTodosFornecedores.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cliente, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().SaldoCliente("fornecedores", dataGuardadaTodosFornecedoresInicio, dataGuardadaTodosFornecedoresFim));
            }
        });
        tblTodosFornecedores.setItems(FXCollections.observableArrayList(Gestor.getInstanceFornecedores().todosClientes()));
        
        
        /***************************************************
         * 
         * seleciona o cliente actual e o fornecedor
         * e verifica se algum deles não está vazio
         * 
         */
        Cliente clienteActual = Gestor.getInstance().getClienteActual();
        Cliente fornecedorActual = Gestor.getInstanceFornecedores().getFornecedorActual();
        
        if(clienteActual != null)
            tblClientes.setItems(FXCollections.observableList(Gestor.getInstance().getClienteActual().getListaCompras("clientes")));
        if(fornecedorActual != null)
            tblFornecedores.setItems(FXCollections.observableList(Gestor.getInstanceFornecedores().getFornecedorActual().getListaCompras("fornecedores")));
        /*************************************************************
         * 
         * Inicia o modal
         * 
         */
        
        initModality(Modality.WINDOW_MODAL);
        if(Gestor.getInstance().getClienteActual() != null && Gestor.getInstanceFornecedores().getFornecedorActual() != null)
            setTitle("Lançamentos - Cliente: " + 
             Gestor.getInstance().getClienteActual().getNome()
             + " | Fornecedor: " + Gestor.getInstanceFornecedores().getFornecedorActual().getNome());
        else if(Gestor.getInstance().getClienteActual() != null)
            setTitle("Lançamentos - Cliente: " + Gestor.getInstance().getClienteActual().getNome());
        else if(Gestor.getInstanceFornecedores().getFornecedorActual() != null)
            setTitle("Lançamentos - Fornecedor:  " + Gestor.getInstanceFornecedores().getFornecedorActual().getNome());
        else
            setTitle("Lançamentos");
        
        initOwner(appStart.getStage());
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
        setResizable(false);
        setFullScreen(false);
        setMaximized(false);
        showAndWait();  
    }
    
    /***************************************
     * 
     * Adiciona uma compra ao cliente
     * @param event 
     */
    
    @FXML
    void adicionarCompraCliente(MouseEvent event) {
        if(Gestor.getInstance().getClienteActual() != null)
            new FXMLAdicionarCompraController(appStart, tblClientes, this);
        else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
       
        lblSaldoCliente.setText(Gestor.getInstance().getClienteActual().SaldoCliente("clientes", null, null).toString());
    }
    
    /***********************************************
     * 
     * Edita uma compra do cliente
     * @param event 
     */
    
    @FXML
    void handleEditarCompraCliente(MouseEvent event) {
        if(Gestor.getInstance().getClienteActual() != null)
            new FXMLEditarCompraController(appStart, tblClientes, this, "cliente");
        else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
    }
    
    /****************************************
     * 
     * Apaga todas as compras do cliente
     * @param event 
     */
    
    @FXML
    void handleApagarTodasComprasCliente(MouseEvent event) {
        if(Gestor.getInstance().getClienteActual() != null) {
            Gestor.getInstance().getClienteActual().removerTodasCompras("clientes");
            List<Compras> compras = Gestor.getInstance().getClienteActual().getListaCompras("clientes");
            compras.sort(Compras.comparator);
            tblClientes.setItems(FXCollections.observableArrayList(compras));
            new FXMLDialogSucessoController(appStart, this, "Lançamentos apagados com sucesso!");
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
    }
    
    /********************************************************
     * 
     * imprime as compras do cliente selecionado
     * @param event 
     */
    
    @FXML
    void imprimirLancamentosCliente(MouseEvent event) {
        try {
            new GerarPDF(tblClientes, Gestor.getInstance().getClienteActual().getNome());
        } catch (IOException ex) {
            new FXMLDialogErrorController(appStart, this, "Não foi possivel imprimir, ocorreu um erro!");
        }
    }
    
    /*********************************************************
     * 
     * Apaga uma compra do cliente
     * @param event 
     */
    
    @FXML
    void handleApagarUmaCompraCliente(MouseEvent event) {
       if(Gestor.getInstance().getClienteActual() != null &&
            tblClientes.getSelectionModel().getSelectedItem() != null) {
        Compras comrpa = tblClientes.getSelectionModel().getSelectedItem();
        Gestor.getInstance().getClienteActual().removerUmaCompra(comrpa, "clientes");
        List<Compras> compras = Gestor.getInstance().getClienteActual().getListaCompras("clientes");
        compras.sort(Compras.comparator);
        tblClientes.setItems(FXCollections.observableArrayList(compras));
        new FXMLDialogSucessoController(appStart, this, "Lançamento apagado com sucesso!");
       } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
    }
    
    /***********************************************************
     * 
     * Visualiza uma compra do cliente
     * @param event 
     */
    @FXML
    void handleVisualizarCompra(MouseEvent event) {
        if(Gestor.getInstance().getClienteActual() != null &&
            tblClientes.getSelectionModel().getSelectedItem() != null) {
                Compras compra = tblClientes.getSelectionModel().getSelectedItem();
                new FXMLVisualizarCompraController(appStart, compra);
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
    }
    
    /*******************************************************
     * 
     * Mostra todos os lancamentos do cliente
     * @param event 
     */
    
    @FXML
    void handleTodosLancamentosCliente(MouseEvent event) {
        if(Gestor.getInstance().getClienteActual() != null) {
            txtDt1.setValue(null);
            txtDt2.setValue(null);
            List<Compras> compras = Gestor.getInstance().getClienteActual().getListaCompras("clientes");
            compras.sort(Compras.comparator);
            tblClientes.setItems(FXCollections.observableArrayList(compras));
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum cliente selecionado!");
           
    }
    
    /**************************************
     * 
     * Acção para fechar o modal
     * @param event 
     */
    
    @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
    /******************************************************
     * 
     * Apaga todas as compras do fornecedor
     * @param event 
     */
    
    @FXML
    void handleApagarTodosFornecedor(MouseEvent event) {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null) {
            Gestor.getInstanceFornecedores().getFornecedorActual().removerTodasCompras("fornecedores");
            tblFornecedores.setItems(FXCollections.observableArrayList(Gestor.getInstanceFornecedores().getFornecedorActual().getListaCompras("fornecedores")));
            new FXMLDialogSucessoController(appStart, this, "Lançamentos apagados com sucesso!");
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
    }

    @FXML
    void handleApagarUmFornecedor(MouseEvent event) {
       if(Gestor.getInstanceFornecedores().getFornecedorActual() != null &&
           tblFornecedores.getSelectionModel().getSelectedItem() != null) {
                Compras compra = tblFornecedores.getSelectionModel().getSelectedItem();
                Gestor.getInstanceFornecedores().getFornecedorActual().removerUmaCompra(compra, "fornecedores");
                List<Compras> compras = Gestor.getInstanceFornecedores().getFornecedorActual().getListaCompras("fornecedores");
                compras.sort(Compras.comparator);
                tblFornecedores.setItems(FXCollections.observableArrayList(compras));
                new FXMLDialogSucessoController(appStart, this, "Lançamento apagado com sucesso!");
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
    }
    
    /**************************************************************
     * 
     * 
     * Edita as compras do fornecedor
     * @param event 
     */
    
    @FXML
    void handleEditarFornecedor(MouseEvent event) {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null)
            new FXMLEditarCompraController(appStart, tblFornecedores, this, "fornecedores");
        else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
    }
    
    /**********************************
     * 
     * Imprime as compras do fornecedor
     * @param event 
     */

    @FXML
    void handleImprimirFornecedores(MouseEvent event) {
        try {
            new GerarPDF(tblFornecedores, Gestor.getInstanceFornecedores().getFornecedorActual().getNome());
        } catch (IOException ex) {
            Logger.getLogger(FXMLLancamentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*****************************************
     * 
     * Visualiza uma compra do fornecedor
     * @param event 
     */
    
    @FXML
    void handleVisualizarCompraFornecedor(MouseEvent event) {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null &&
             tblFornecedores.getSelectionModel().getSelectedItem() != null) { 
                    Compras compra = tblFornecedores.getSelectionModel().getSelectedItem();
                    new FXMLVisualizarCompraController(appStart, compra);
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
    }
    
    /****************************************************
     * 
     * Cria uma nova compra para o fornecedor
     * @param event 
     */

    @FXML
    void handleNovoLancamentoFornecedor(MouseEvent event) {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null)
            new FXMLAdicionarCompraFornecedorController(appStart, tblFornecedores, this);
        else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
        
        lblSaldoFornecedor.setText(Gestor.getInstanceFornecedores().getFornecedorActual().SaldoCliente("fornecedores", null, null).toString());
    }
    
    /**************************************************
     * 
     * Mostra todos os lançamentos do fornecedor
     * @param event 
     */
    
    @FXML
    void handleTodosLancamentosFornecedor(MouseEvent event) {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null) {
            txtDtFornecedores1.setValue(null);
            txtDtFornecedores2.setValue(null);
            List<Compras> compras = Gestor.getInstanceFornecedores().getFornecedorActual().getListaCompras("fornecedores");
            compras.sort(Compras.comparator);
            tblFornecedores.setItems(FXCollections.observableArrayList(compras));
        } else
            new FXMLDialogErrorController(appStart, this, "Não tem nenhum fornecedor selecionado!");
    }
    
    @FXML
    void handleImprimirTodosLancamentosClientes(MouseEvent event) {
        try {
            new GerarPDFTodos(tblTodosClientes, "Todos os Clientes", dataGuardadaTodosClientesInicio, dataGuardadaTodosClientesFim, "clientes");
        } catch (IOException ex) {
            Logger.getLogger(FXMLLancamentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleImprimirTodosLancamentosFornecedor(MouseEvent event) {
        try {
            new GerarPDFTodos(tblTodosFornecedores, "Todos os Fornecedores", dataGuardadaTodosFornecedoresInicio, dataGuardadaTodosFornecedoresFim, "fornecedores");
        } catch (IOException ex) {
            Logger.getLogger(FXMLLancamentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @FXML
    void handleTodosDocumentosTodosClientes(MouseEvent event) {
        txtDtTodosClientes1.setValue(null);
        txtDtTodosClientes2.setValue(null);
        tblTodosClientes.refresh();
    }

    @FXML
    void handleTodosDocumentosTodosFornecedores(MouseEvent event) {
        txtDtTodosFornecedores1.setValue(null);
        txtDtTodosFornecedores2.setValue(null);
        tblTodosFornecedores.refresh();
    }
    
    /**************************************
     * 
     * Inicializa os componentes e suas
     * acções ao iniciar o modal
     */
    
    @FXML
    public void initialize() {
        /********************
         * mostrar compras feitas na data selecionada no primeiro datepicker
         * do lado do cliente
         */
        txtDt1.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(Gestor.getInstance().getClienteActual().getListaComprasDia(newValue, "clientes") == null) 
                tblClientes.setItems(null);
            else  {
                this.dataGuardada= newValue;
                List<Compras> compras = Gestor.getInstance().getClienteActual().getListaComprasDia(newValue, "clientes");
                compras.sort(Compras.comparator);
                tblClientes.setItems(FXCollections.observableArrayList());
            }
        });
        
        /********************************************************
         * selecionar entre datas do lado do cliente
         */
        
        txtDt2.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(Gestor.getInstance().getClienteActual().getListaComprasEntreDatas(dataGuardada, newValue, "clientes") == null)
                tblClientes.setItems(null);
            else {
                List<Compras> compras = Gestor.getInstance().getClienteActual().getListaComprasEntreDatas(dataGuardada, newValue, "clientes");
                compras.sort(Compras.comparator);
                tblClientes.setItems(FXCollections.observableArrayList(compras));
            }

        });
        
        /****************************************
         * selecionar compra numa data do lado do fornecedor
         */
        txtDtFornecedores1.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(Gestor.getInstanceFornecedores().getFornecedorActual().getListaComprasDia(newValue, "fornecedores") == null) 
                tblFornecedores.setItems(null);
            else  {
                this.dataGuardadaFornecedores= newValue;
                List<Compras> compras = Gestor.getInstanceFornecedores().getFornecedorActual().getListaComprasDia(newValue, "fornecedores");
                compras.sort(Compras.comparator);
                tblFornecedores.setItems(FXCollections.observableArrayList(compras));
            }
        });
        /********************************************************
         * selecionar entre datas do lado do fornecedor
         */
        
        txtDtFornecedores2.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(Gestor.getInstanceFornecedores().getFornecedorActual().getListaComprasEntreDatas(dataGuardadaFornecedores, newValue, "fornecedores") == null)
                tblFornecedores.setItems(null);
            else {
                List<Compras> compras = Gestor.getInstanceFornecedores().getFornecedorActual().getListaComprasEntreDatas(dataGuardadaFornecedores, newValue, "fornecedores");
                compras.sort(Compras.comparator);
                tblFornecedores.setItems(FXCollections.observableArrayList(compras));
            }

        });
        
         /****************************************
         * selecionar compra numa data do lado de
         * todos os fornecedores
         */
        txtDtTodosFornecedores1.valueProperty().addListener((ov, oldValue, novaDataInicio) -> {
            dataGuardadaTodosFornecedoresInicio = novaDataInicio;
            tblTodosFornecedores.refresh();
        });
        /********************************************************
         * selecionar entre datas de todos os fornecedores
         * 
         */
        
        txtDtTodosFornecedores2.valueProperty().addListener((ov, oldValue, novaDataFim) -> {
            dataGuardadaTodosFornecedoresFim = novaDataFim;
            tblTodosFornecedores.refresh();
        });
        
        /****************************************
         * selecionar compra numa data do lado de
         * todos os clientes
         */
        txtDtTodosClientes1.valueProperty().addListener((ov, oldValue, novaDataInicio) -> {
            dataGuardadaTodosClientesInicio = novaDataInicio;
            tblTodosClientes.refresh();
        });
        /********************************************************
         * selecionar entre datas de todos os clientes
         * 
         */
        
        txtDtTodosClientes2.valueProperty().addListener((ov, oldValue, novaDataFim) -> {
            dataGuardadaTodosClientesFim = novaDataFim;
            tblTodosClientes.refresh();
        });
        
        /****************************************************
         * Se o cliente ou o fornecedor estiver selecionado
         * mostrar o saldo do cliente na lbl respectiva
         */
        if(Gestor.getInstance().getClienteActual() != null)
            lblSaldoCliente.setText(Gestor.getInstance().getClienteActual().SaldoCliente("clientes", null, null).toString());
        if(Gestor.getInstanceFornecedores().getFornecedorActual()!= null)
            lblSaldoFornecedor.setText(Gestor.getInstanceFornecedores().getFornecedorActual().SaldoCliente("fornecedores", null, null).toString());
        /***********************************************************
         * Verificar se tem clientes ou fornecedores carregados
         * se não tiver então carrega os dois
         */
        if(!Gestor.getInstance().isClientesCarregados()) {
            Gestor.getInstance().load();
            Gestor.getInstance().setClientesCarregados(true);
        }
        if(!Gestor.getInstanceFornecedores().isFornecedoresCarregados()) {
            Gestor.getInstanceFornecedores().load();
            Gestor.getInstanceFornecedores().setFornecedoresCarregados(true);
        }
    }   
   
}
