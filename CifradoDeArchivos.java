import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CifradoDeArchivos extends JFrame implements ActionListener{

	String min = "abcdefghijklmnopqrstuvwxyz{|}~ ";
	String may = "ABCDEFGHIJKLMNOPQRSTUVWXYZ[/]^ ";
	String num = "0123456789";
    
	private JTextField textField;
	private JRadioButton planoJRadioButton;
	private JRadioButton cifradoJRadioButton;
	private ButtonGroup grupoOpciones;
	private JButton boton;
	private JButton boton2;
	private FileWriter fw;

    public CifradoDeArchivos(){
        super("Cifrado de archivos");
        setLayout(new FlowLayout());

        textField = new JTextField(20);
		add(textField);

		planoJRadioButton = new JRadioButton("Texto Plano", true);
		cifradoJRadioButton = new JRadioButton("Texto Cifrado", false);
        add(planoJRadioButton);
		add(cifradoJRadioButton);
		grupoOpciones = new ButtonGroup();
        grupoOpciones.add(planoJRadioButton);
        grupoOpciones.add(cifradoJRadioButton);

		planoJRadioButton.addItemListener(new ManejadorBotonOpcion());
		cifradoJRadioButton.addItemListener(new ManejadorBotonOpcion());

		boton = new JButton("Guardar TXT");
		add(boton);
		boton.setBounds(205,220,150,30);
		boton.addActionListener(this);

		boton2 = new JButton("Guardar VGE");
		add(boton2);
		boton2.setBounds(205,220,150,30);
		boton2.addActionListener(this);
    }

	private class ManejadorBotonOpcion implements ItemListener{
    
        public void itemStateChanged(ItemEvent evento){

			String valor = textField.getText();
			String convertido;
			if(cifradoJRadioButton.isSelected()){
				convertido = cifrado(valor);
				textField.setText(convertido);
			}else if(planoJRadioButton.isSelected()){
				convertido = descifrado(valor);
				textField.setText(convertido);
			}
        }
    }


	private String cifrado(String texto){

		String texto2 = "";
		String invert = "";
		String aux = "";
		String mitad = "";

		for(int i = 0; i < texto.length(); i++){
			for(int j = 0; j < min.length(); j++){
				if(texto.charAt(i) == min.charAt(j)){
					if(j+3 >= min.length()){
						texto2 += min.charAt((j + 3) % min.length());
					}else{
						texto2 += min.charAt(j + 3);
					}
				}else if(texto.charAt(i) == may.charAt(j)){
					if(j+3 >= may.length()){
						texto2 += may.charAt((j + 3) % may.length());
					}else{
						texto2 += may.charAt(j + 3);
					}
				}
			}
			for(int x = 0; x < num.length(); x++){
				if(texto.charAt(i) == num.charAt(x)){
					texto2 += num.charAt(x);
				}
			}
		}

		for(int i = texto2.length()-1 ; i >= 0; i--){
			invert += texto2.charAt(i);
		}
		texto2 = invert;

		for(int i = texto2.length()/2; i < texto2.length(); i++){
			for(int j = 0; j < min.length(); j++){
				if(texto2.charAt(i) == min.charAt(j)){
					if(j-1 >= min.length()){
						aux += min.charAt((j - 1) % min.length());
					}else{
						aux += min.charAt(j -1);
					}
				}else if(texto2.charAt(i) == may.charAt(j)){
					if(j-1 >= may.length()){
						aux += may.charAt((j - 1) % may.length());
					}else{
						aux += may.charAt(j - 1);
					}
				}
			}
			for(int x = 0; x < num.length(); x++){
				if(texto2.charAt(i) == num.charAt(x)){
					aux += num.charAt(x);
				}
			}
		}

		for(int i = 0; i < texto2.length()/2; i++){
			mitad += texto2.charAt(i);
		}
		
		mitad += aux;
		texto2 = mitad;
		texto = texto2;
		return texto;
		//texto2 += aux;
	}

	private String descifrado(String texto){

		String texto2 = "";
		String invert = "";
		String aux = "";
		String mitad = "";
		String desif = "";

		for(int i = texto.length()/2; i < texto.length(); i++){
			for(int j = 0; j < min.length(); j++){
				if(texto.charAt(i) == min.charAt(j)){
					if(j+1 >= min.length()){
						aux += min.charAt((j + 1) % min.length());
					}else{
						aux += min.charAt(j + 1);
					}
				}else if(texto.charAt(i) == may.charAt(j)){
					if(j+1 >= may.length()){
						aux += may.charAt((j + 1) % may.length());
					}else{
						aux += may.charAt(j + 1);
					}
				}
			}
			for(int x = 0; x < num.length(); x++){
				if(texto.charAt(i) == num.charAt(x)){
					aux += num.charAt(x);
				}
			}
		}

		for(int i = 0; i < texto.length()/2; i++){
			mitad += texto.charAt(i);
		}
		
		mitad += aux;
		texto2 = mitad;

		for(int i = texto2.length()-1 ; i >= 0; i--){
			invert += texto2.charAt(i);
		}
		texto2 = invert;

		for(int i = 0; i < texto2.length(); i++){
			for(int j = 0; j < min.length(); j++){
				if(texto2.charAt(i) == min.charAt(j)){
					if(j-3 >= min.length()){
						desif += min.charAt((j - 3) % min.length());
					}else{
						desif += min.charAt(j - 3);
					}
				}else if(texto2.charAt(i) == may.charAt(j)){
					if(j-3 >= may.length()){
						desif += may.charAt((j - 3) % may.length());
					}else{
						desif += may.charAt(j - 3);
					}
				}
			}
			for(int x = 0; x < num.length(); x++){
				if(texto2.charAt(i) == num.charAt(x)){
					desif += num.charAt(x);
				}
			}
		}

		texto = desif;
		return texto;
		//texto2 += aux;
	}

	private void exportarTXT(){

		String ruta = "/home/christopher/Documentos/Java/CifradoDeArchivos/TextoPlano.txt";
		File file = new File(ruta);
		String t = textField.getText();

		try {
			if(!file.exists()){
				file.createNewFile();
			}
			fw = new FileWriter(file);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(t);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportarVGE(){
		String ruta = "/home/christopher/Documentos/Java/CifradoDeArchivos/TextoCifrado.vge";
		File file = new File(ruta);
		String t = textField.getText();

		try {
			if(!file.exists()){
				file.createNewFile();
			}
			fw = new FileWriter(file);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(t);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == boton){
			exportarTXT();
		}
		if(e.getSource() == boton2){
			exportarVGE();
		}
	}
}