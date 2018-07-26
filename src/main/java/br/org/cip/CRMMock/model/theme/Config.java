package br.org.cip.CRMMock.model.theme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
public class Config {

	private static Config instance;
	
	public static Config getInstance(){
        if(instance == null){
            instance = new Config();
//            instance.setId(1L);
        }
        return instance;
    }
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "theme",unique=true,nullable=false)
	private  ThemeType themeType;

	public  ThemeType getThemeType() {
		return themeType;
	}

	public  void setThemeType(ThemeType theme) {
		this.themeType = theme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
