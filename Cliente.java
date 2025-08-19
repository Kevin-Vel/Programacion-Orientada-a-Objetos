public class Cliente {
    private int idcli, edad;
    private String nom, apell, sex;

    public Cliente(int idcli, int edad, String nom, String apell, String sex) {
        this.idcli = idcli;
        this.edad = edad;
        this.nom = nom;
        this.apell = apell;
        this.sex = sex;
    }

    public String getNom() {return nom;}
    public String getApell(){return apell;}
    public String getSex(){return sex;}
    public int getIdcli(){return idcli;}
    public int getEdad(){return edad;}

    public void setNom(String nom){this.nom=nom;}
    public void setApell(String apell){this.apell=apell;}
    public void setSex(String sex){this.sex=sex;}
    public void setIdcli(int idcli){this.idcli=idcli;}
    public void setEdad(int edad){this.edad=edad;}
}


