package de.saxsys.jfx.exampleapplication.viewmodel.personwelcome;

import de.saxsys.jfx.exampleapplication.model.Gender;
import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

/**
 * ViewModel for a welcome view for a person. It provides the data which should be visualized in the frontend. The tests
 * for it can be written first. Have a look on PersonWelcomeViewModelTest.
 * 
 * @author alexander.casall
 * 
 */
public class PersonWelcomeViewModel implements ViewModel {
	
	private Repository repository;
	
	// Properties which are used by the view.
	private final StringProperty welcomeString = new SimpleStringProperty();
	
	private Person person;
	
	/**
	 * Create a {@link PersonWelcomeViewModel}.
	 * 
	 * @param repository
	 *            repo which is used
	 */
	@Inject
	public PersonWelcomeViewModel(Repository repository) {
		this.repository = repository;
	}
	
	/**
	 * Provides the the concated string.
	 * 
	 * @return
	 */
	public StringProperty welcomeStringProperty() {
		return welcomeString;
	}
	
	/**
	 * Set Person id for the screen
	 * 
	 * @param personId
	 *            for the screen
	 */
	public void setPersonId(int personId) {
		person = repository.getPersonById(personId);
		
		StringBinding salutationBinding =
				Bindings.when(person.genderProperty().isEqualTo(Gender.NOT_SPECIFIED))
						.then("Herr/Frau/* ")
						.otherwise(
								Bindings.when(person.genderProperty().isEqualTo(Gender.MALE))
										.then("Herr ").otherwise("Frau "));

		welcomeString.unbind();
		welcomeString.bind(Bindings.concat("Willkommen ", salutationBinding,
				person.lastNameProperty(), ", oder wollen Sie ",
				person.firstNameProperty(), " genannt werden?"));
	}
	
	/**
	 * Returns the id of the displayed person.
	 * 
	 * @return id
	 */
	public int getPersonId() {
		return person.getId();
	}
}
