package org.academy.hibernate.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name = "jpa07_hb_activite")
public class Activite implements Serializable {

	// champs
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Version
	private int version;

	@Column(length = 30, nullable = false, unique = true)
	private String nom;

	// relation Activite (one) -> PersonneActivite (many)
	// inverse de la relation existante PersonneActivite (many) -> Activite (one)
	// cascade suppression Activite -> supression PersonneActivite
	@OneToMany(mappedBy = "activite", cascade = { CascadeType.REMOVE })
	private Set<PersonneActivite> personnes = new HashSet<PersonneActivite>();

	// constructeurs
	public Activite() {

	}

	public Activite(Long id, int version, String nom) {
		super();
		setId(id);
		setVersion(version);
		setNom(nom);
	}

	// getters et setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<PersonneActivite> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(Set<PersonneActivite> personnes) {
		this.personnes = personnes;
	}

	// toString
	public String toString() {
		return String.format("Ac[%d,%d,%s]", getId(), getVersion(), getNom());
	}
}
