/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Proyecto2.Cliente;
import Proyecto2.DateSistema;
import Proyecto2.Empresa;
import Proyecto2.Individual;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rogera.gonzalez
 */
public class GridClienteController implements Initializable {

    private TextField txtDescuento;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtDPI;
    private TextField txtContacto;
    @FXML
    private Label txtCliente;
    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colEdad;
    @FXML
    private TableColumn colDPI;
    private TableColumn colContacto;
    private TableColumn colDescuento;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableColumn colID;
    @FXML
    private TextField txtID;
    
    private ObservableList<Cliente> clientes;
    @FXML
    private Button btnModificart;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creo el observablelist
        clientes = FXCollections.observableArrayList();

        // Asigno las columnas con los atributos del modelo
        this.colNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("Apellido"));
        this.colEdad.setCellValueFactory(new PropertyValueFactory("Edad"));
        this.colID.setCellValueFactory(new PropertyValueFactory("Id"));
        this.colDPI.setCellValueFactory(new PropertyValueFactory("DPI"));
        
        
    }

    public void closeWindows() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuVista.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnAgregar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnAgregando(ActionEvent event) {
        
        try {

            // Obtengo los datos del formulario
            String Nombre = this.txtNombre.getText();
            String Apellido = this.txtApellidos.getText();
            String Edad = this.txtEdad.getText();
            int Id = Integer.parseInt(this.txtID.getText());
            String Dpi = this.txtDPI.getText();
            

            // Creo una persona
            //Cliente c = new Cliente(Nombre, Apellido, Edad, Id, Dpi, Contacto, Descuento );
            Cliente c1 = new Individual(Nombre, Apellido, Edad, Id, Dpi);
            //c = new Empresa (Contacto, Descuento);
            

            // Compruebo si la persona esta en el lista
            if (!this.clientes.contains(c1)) {
                // Lo añado a la lista
                this.clientes.add(c1);
                // Seteo los items
                this.tblClientes.setItems(clientes);
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Cliente o DPI ya existente");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato incorrecto");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        
        // Obtengo la persona seleccionada
        Cliente c1 = this.tblClientes.getSelectionModel().getSelectedItem();

        // Sino es nula seteo los campos
        if (c1 != null) {
            this.txtNombre.setText(c1.getNombre());
            this.txtApellidos.setText(c1.getApellido());
            this.txtEdad.setText(c1.getEdad());
            this.txtID.setText(c1.getId()+"");
            //this.txtDPI.setText(c.getDPI()+"");
        }
        
    }

    @FXML
    private void btnModifico(ActionEvent event) {
        
        // Obtengo la persona seleccionada
        Cliente c1 = this.tblClientes.getSelectionModel().getSelectedItem();

        // Si la persona es nula, lanzo error
        if (c1 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un Cliente");
            alert.showAndWait();
        } else {

            try {
                // Obtengo los datos del formulario
                String Nombre = this.txtNombre.getText();
                String Apellido = this.txtApellidos.getText();
                String Edad = this.txtEdad.getText();
                int Id = Integer.parseInt(this.txtID.getText());
                String Dpi = this.txtDPI.getText();

                // Creo una persona
                Cliente aux1 = new Cliente(Nombre, Apellido, Edad, Id);

                // Compruebo si la persona esta en el lista
                if (!this.clientes.contains(aux1)) {

                    // Modifico el objeto
                    c1.setNombre(aux1.getNombre());
                    c1.setApellido(aux1.getApellido());
                    c1.setEdad(aux1.getEdad());
                    c1.setId(aux1.getId());

                    // Refresco la tabla
                    this.tblClientes.refresh();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Info");
                    alert.setContentText("Cliente modificado");
                    alert.showAndWait();

                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Cliente o DPI ya existente");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Formato incorrecto");
                alert.showAndWait();
            }

        }
        
    }

    @FXML
    private void btnElimando(ActionEvent event) {
        
        // Obtengo la persona seleccionada
        Cliente c1 = this.tblClientes.getSelectionModel().getSelectedItem();

        // Si la persona es nula, lanzo error
        if (c1 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un Cliente");
            alert.showAndWait();
        } else {

            // La elimino de la lista
            this.clientes.remove(c1);
            // Refresco la lista
            this.tblClientes.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Cliente eliminado");
            alert.showAndWait();

        }
    }
    
}
