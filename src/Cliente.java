public class Cliente {
    private int idcli, dni;
    private String nom, apell, sex;

    public Cliente(int idcli, String nom, String apell) {
        this.idcli = idcli;
        this.nom = nom;
        this.apell = apell;
        this.dni=dni;
    }

    public String getNom() {return nom;}
    public String getApell(){return apell;}
    public int getIdcli(){return idcli;}
    public int getDni(){return dni;}

    public void setNom(String nom){this.nom=nom;}
    public void setApell(String apell){this.apell=apell;}
    public void setIdcli(int idcli){this.idcli=idcli;}
    public void setDni(int dni){this.dni=dni;}
}


