import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Phylomon {
	private String name1;
	private int HP1;
	private String attackName1;
	private int attackDamage1;
	private String picLocatie1;
	
	
	Phylomon(String name,int Hp,String attackName,int attackDamage, String picLocatie){
		this.name1 = name;
		this.HP1 = Hp;
		this.attackName1 = attackName;
		this.attackDamage1 = attackDamage;	
		if(picLocatie.equals("")){
			this.picLocatie1 = "no pic locatie";
		}else{
			this.picLocatie1 = picLocatie;
		}
	}
	
	//de constructor die de DBM gebruikt om van xml phylomon te maken
	Phylomon(Element element){
		this.name1 = DBM.getValue("Name", element);
		this.HP1 = Integer.valueOf(DBM.getValue("HP", element));
		this.attackName1 = DBM.getValue("attackName", element);
		this.attackDamage1 = Integer.valueOf(DBM.getValue("attackDamage", element));	
		this.picLocatie1 = DBM.getValue("picLocatie", element);
		
		

	}
	
	public Element toElement(Document doc,Element rootElement){
		Element phylomon = doc.createElement("phylomon");
		//de attributen van de phylomon maken
		Element name = doc.createElement("Name");
		name.appendChild(doc.createTextNode(this.getName()));
		Element hp = doc.createElement("HP");
		hp.appendChild(doc.createTextNode(Integer.toString(this.getHp())));
		Element attackName = doc.createElement("attackName");
		attackName.appendChild(doc.createTextNode(this.getAttackName()));
		Element attackDamage = doc.createElement("attackDamage");
		attackDamage.appendChild(doc.createTextNode(Integer.toString(this.getAttackDamage())));
		Element picLocatie = doc.createElement("picLocatie");
		picLocatie.appendChild(doc.createTextNode(this.getPicLocatie()));
		//attributen toevoegen
		phylomon.appendChild(name);
		phylomon.appendChild(hp);
		phylomon.appendChild(attackName);
		phylomon.appendChild(attackDamage);
		phylomon.appendChild(picLocatie);
		
		
		return phylomon;		
	}
	
	
	
	
	
	
	
	//getters:
	public String getName(){
		return name1;
	}
	public int getHp(){
		return HP1;
	}
	public String getAttackName(){
		return attackName1;
	}
	public int getAttackDamage(){
		return attackDamage1;
	}
	public String getPicLocatie(){
		return picLocatie1;
	}
	//setters:
	public void setName(String name){
		name1 = name;
	}
	public void setHp(int Hp){
		HP1 = Hp;
	}
	public void setAttackName(String attackName ){
		attackName1 = attackName;
	}
	public void setAttackDamage(int AttackDamage){
		attackDamage1 = AttackDamage;
	}
	public void setPicLocatie(String picLocatie){
		picLocatie1 = picLocatie;
	}
	
}
