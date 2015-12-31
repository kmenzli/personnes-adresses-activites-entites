package org.academy.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@SuppressWarnings( { "serial", "unused" })
// table de jointure
@Table(name = "jpa07_hb_personne_activite")
public class PersonneActivite {

	@Embeddable
	public static class Id implements Serializable {
		// composantes de la cl� composite
		// pointe sur une Personne
		@Column(name = "PERSONNE_ID")
		private Long personneId;

		// pointe sur une Activite
		@Column(name = "ACTIVITE_ID")
		private Long activiteId;

		// constructeurs
		public Id() {
		}

		// getters et setters

		public Id(Long personneId, Long activiteId) {
			super();
			this.personneId = personneId;
			this.activiteId = activiteId;
		}

		public Long getPersonneId() {
			return personneId;
		}

		public void setPersonneId(Long personneId) {
			this.personneId = personneId;
		}

		public Long getActiviteId() {
			return activiteId;
		}

		public void setActiviteId(Long activiteId) {
			this.activiteId = activiteId;
		}

		// toString
		public String toString() {
			return String.format("[%d,%d]", getPersonneId(), getActiviteId());
		}
	}

	// champs de la classe Personne_Activite
	// cl� composite
	@EmbeddedId
	private Id id = new Id();

	// relation principale PersonneActivite (many) -> Personne (one)
	// impl�ment�e par la cl� �trang�re : personneId (PersonneActivite (many) -> Personne (one)
	// personneId est en m�me temps �l�ment de la cl� primaire composite
	// JPA ne doit pas g�rer cette cl� �trang�re (insertable = false, updatable = false) car c'est fait par l'application
	// elle-m�me dans son
	// constructeur
	@ManyToOne
	@JoinColumn(name = "PERSONNE_ID", insertable = false, updatable = false)
	private Personne personne;

	// relation principale PersonneActivite -> Activite
	// impl�ment�e par la cl� �trang�re : activiteId (PersonneActivite (many) -> Activite (one)
	// activiteId est en m�me temps �l�ment de la cl� primaire composite
	// JPA ne doit pas g�rer cette cl� �trang�re (insertable = false, updatable = false) car c'est fait par l'application
	// elle-m�me dans son
	// constructeur
	@ManyToOne()
	@JoinColumn(name = "ACTIVITE_ID", insertable = false, updatable = false)
	private Activite activite;

	// constructeurs
	public PersonneActivite() {

	}

	public PersonneActivite(Personne p, Activite a) {
		// les cl�s �trang�res sont fix�es par l'application
		getId().setPersonneId(p.getId());
		getId().setActiviteId(a.getId());
		// associations bidirectionnelles
		setPersonne(p);
		setActivite(a);
		p.getActivites().add(this);
		a.getPersonnes().add(this);
	}

	// getters et setters
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Activite getActivite() {
		return activite;
	}

	public void setActivite(Activite activite) {
		this.activite = activite;
	}

	// toString
	public String toString() {
		return String.format("[%s,%s,%s]", getId(), getPersonne().getNom(), getActivite().getNom());
	}
}
