
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.*;




public class Gui extends JFrame {
	private DBM database;
	private ListIterator<Phylomon> itt;
	private boolean ittDirection;
	private Phylomon current;
	
	private JTabbedPane tabs;
    private JPanel panel1,panel1N,panel1Z,panel1NW,panel1NE;
    private JPanel panel2,panel2Z;
    private JScrollPane panel2N;
    //het eerste nummer staat voor de tab
    //leters N,O,Z,W staan voor border alignment
	private JTextField nameText,hpText,attackNameText,attackDamageText,pictureText;
	private JLabel nameLabel,hPLabel,attackNameLabel,attackDamageLabel,pictureLabel,picLabel;
	private JList list;
	private DefaultListModel listModel;
	private JButton saveBut,clearBut,nextBut,previousBut,searchBut;
	private ImageIcon imag;
	
    public Gui() {
    	super("phylomon database manager");
    	database = new DBM();
    	itt = database.getPhylomonIterator();
    	
    	loadFrame();
    	loadList();
    }
   
    private void loadFrame(){
    	//een tabbed pane maken
    	tabs = new JTabbedPane();
    	add(tabs);
    	//twee tabs
    	panel1 = new JPanel(new BorderLayout());
    	panel2 = new JPanel(new BorderLayout()); 
    	//toevoegen aan tabs
    	tabs.addTab("phylomon", panel1);
    	tabs.addTab("database", panel2);
    	
    	//panel1 initialiseren:
    	panel1N = new JPanel(new BorderLayout());
    	panel1.add(panel1N,BorderLayout.NORTH);    	
    	panel1Z = new JPanel();
    	new BoxLayout(panel1Z,BoxLayout.LINE_AXIS);    	
    	panel1.add(panel1Z,BorderLayout.SOUTH);
    	
    	//panel1N initialiseren:
    	panel1NW = new JPanel(new GridLayout(5,2));
    	panel1N.add(panel1NW,BorderLayout.WEST);
    	panel1NE = new JPanel(new FlowLayout()); 
    	panel1N.add(panel1NE,BorderLayout.EAST);

    	//panel1NW initialiseren: 	
    	/*name*/
    	nameLabel = new JLabel("phylomon name:");
    	panel1NW.add(nameLabel);
    	nameText = new JTextField(10);
    	panel1NW.add(nameText);
    	/*HP*/
    	hPLabel = new JLabel("maximum hitpoints:");
    	panel1NW.add(hPLabel);
    	hpText = new JTextField(10);
    	panel1NW.add(hpText);
    	/*attackname*/
    	attackNameLabel = new JLabel("attack name:");
    	panel1NW.add(attackNameLabel);
    	attackNameText = new JTextField(10);
    	panel1NW.add(attackNameText);
    	/*attackdamage*/
    	attackDamageLabel = new JLabel("attack damage:");
    	panel1NW.add(attackDamageLabel);
    	attackDamageText = new JTextField(10);
    	panel1NW.add(attackDamageText);
    	/*picture*/
    	pictureLabel = new JLabel("picture location:");
    	panel1NW.add(pictureLabel);
    	pictureText = new JTextField(10);
    	panel1NW.add(pictureText);
    	picLabel = new JLabel();
    	panel1NE.add(picLabel );
    	
    	//panel1Z initialiseren:
    	Handler handler = new Handler();
    	
    	previousBut = new JButton("<-- previous");
    	previousBut.addActionListener(handler);
    	panel1Z.add(previousBut);
    	
    	clearBut = new JButton("clear");
    	clearBut.addActionListener(handler);
    	panel1Z.add(clearBut);
    	
    	saveBut = new JButton("save");
    	saveBut.addActionListener(handler);
    	panel1Z.add(saveBut);
    	
    	nextBut = new JButton("next -->");
    	nextBut.addActionListener(handler);
    	panel1Z.add(nextBut);
    	
    	panel1Z.setMaximumSize(nextBut.getMaximumSize());
    	
    	//panel2 initialiseren:
    	//panel2N initialiseren:
    	listModel = new DefaultListModel();
    	list = new JList(listModel);
    	list.setVisibleRowCount(9);
    	list.addMouseListener(new mouseHandler());
    	panel2N=  new JScrollPane(list);
    	panel2.add(panel2N,BorderLayout.NORTH);    	
    	panel2Z = new JPanel();
    	new BoxLayout(panel2Z,BoxLayout.LINE_AXIS);    	
    	panel2.add(panel2Z,BorderLayout.SOUTH);
    	
    	//panel2Z initialiseren:
    	searchBut = new JButton("search");
    	searchBut.addActionListener(handler);
    	panel2Z.add(searchBut);
    }
    
    private void loadPhylomon(Phylomon phylomon){
    	current = phylomon;
    	nameText.setText(current.getName());
    	hpText.setText(Integer.toString(current.getHp()));
    	attackNameText.setText(current.getAttackName());
    	attackDamageText.setText(Integer.toString(current.getAttackDamage()));
    	pictureText.setText(current.getPicLocatie());
    	
    	try{
    		imag = new ImageIcon(current.getPicLocatie());
        	picLabel.setIcon(imag);
    	}catch(Exception e){}
    	
    }
    
    private void clearSchreen(){
    	itt = database.getPhylomonIterator();
    	ittDirection = true;
    	current = null;
    	nameText.setText("");
    	hpText.setText("");
    	attackNameText.setText("");
    	attackDamageText.setText("");
    	pictureText.setText("");

    	picLabel.setIcon(null);
    	this.validate();
    }
    
    private void save(){
    	if(current == null){
    		if(nameText.getText().equals("")){
    			return;
    		}else{
    			database.addPhylomon(new Phylomon(	nameText.getText(),
													Integer.valueOf(hpText.getText()),
    	    										attackNameText.getText(),
													Integer.valueOf(attackDamageText.getText()),
													pictureText.getText()
													
													
    												));
    		}	
    	}else{
    		if(nameText.getText().equals("")){
    			nameText.setText(current.getName());
    		}else{
    			current.setName(nameText.getText());
    		}
    		current.setAttackName(attackNameText.getText());
    		current.setHp(Integer.valueOf(hpText.getText()));
    		current.setAttackDamage(Integer.valueOf(attackDamageText.getText()));
    		current.setPicLocatie(pictureText.getText());
    	}
    	database.save();
    }
    
    private void loadList(){
    	for(int i = 0;itt.hasNext();){
    		i++;
    		Phylomon current = itt.next();
    		listModel.addElement(i + "  " + current.getName()); 		
    	}
    	itt= database.getPhylomonIterator();  	
    }
    
    private class mouseHandler extends MouseAdapter{
    	public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                itt = database.getPhylomonIterator(list.getSelectedIndex());
                loadPhylomon(itt.next());
                tabs.setSelectedComponent(panel1);
            } 
        }
    }
     
    private class Handler implements ActionListener{  	
    	public void actionPerformed(ActionEvent event){
    		
    		Object source = event.getSource();
    		if(source.equals(clearBut)){
    			clearSchreen();
    		}else if(source.equals(saveBut)){
    			save();
    		
    		}else if(source.equals(nextBut)){
    			if(itt.hasNext()){
    				loadPhylomon(itt.next());
    			}
    		}else if(source.equals(previousBut)){
    			if(itt.hasPrevious()){
    				itt.previous();
    				if(itt.hasPrevious()){
    					loadPhylomon(itt.previous());
    					
    				}
    				itt.next();
    			}
    		}
    		
    	}
    	
    }
   
    
 
}