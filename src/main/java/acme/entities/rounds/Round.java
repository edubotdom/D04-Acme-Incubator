
package acme.entities.rounds;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.entities.activities.Activity;
import acme.entities.roles.Entrepreneur;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Round extends DomainEntity {

	// Serialisation identifier
	private static final long		serialVersionUID	= 1L;

	// Atributos
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{3}X{0,2}-[0-9]{2}-[0-9]{6}$")
	@NotBlank
	private String					ticker;

	@NotNull
	@Past
	private Date					creation;

	@Pattern(regexp = "^(SEED|ANGEL|SERIES-A|SERIES-B|SERIES-C|BRIDGE)$")
	@NotBlank
	private String					kind;

	@NotBlank
	private String					title;

	@NotBlank
	private String					description;

	@Valid
	@NotNull
	private Money					money;

	@NotBlank
	@URL
	private String					information;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Entrepreneur			entrepreneur;

	//Work programme
	@Valid
	@OneToMany
	private Collection<Activity>	activities;
}
