
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import acme.entities.cards.Card;
import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patron extends UserRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				organisation;

	@Valid
	private Card				card;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
