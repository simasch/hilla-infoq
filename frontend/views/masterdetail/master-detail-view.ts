import {Binder, field} from '@hilla/form';
import '@vaadin/button';
import '@vaadin/grid';
import '@vaadin/horizontal-layout';
import '@vaadin/form-layout';
import '@vaadin/text-field';
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {View} from '../view';
import PersonModel from 'Frontend/generated/ch/martinelli/demo/hilla/entity/PersonModel';
import Person from 'Frontend/generated/ch/martinelli/demo/hilla/entity/Person';
import {PersonEndpoint} from 'Frontend/generated/endpoints';

@customElement('master-detail-view')
export class MasterDetailView extends View {

    @state()
    people: Person[] = [];

    private binder = new Binder<Person, PersonModel>(this, PersonModel);
    private selectedPerson?: Person;

    async connectedCallback() {
        super.connectedCallback();

        this.people = await PersonEndpoint.findAll();
    }

    render() {
        return html`
            <vaadin-grid .items=${this.people}
                         @active-item-changed=${this.itemSelected}
                         .selectedItems=${[this.selectedPerson]}
            >
                <vaadin-grid-column path="firstName"></vaadin-grid-column>
                <vaadin-grid-column path="lastName"></vaadin-grid-column>
                <vaadin-grid-column path="email"></vaadin-grid-column>
            </vaadin-grid>

            <vaadin-form-layout>
                <vaadin-text-field
                        label="First name"
                        ${field(this.binder.model.firstName)}
                ></vaadin-text-field>
                <vaadin-text-field
                        label="Last name"
                        ${field(this.binder.model.lastName)}
                ></vaadin-text-field>
                <vaadin-text-field
                        label="Email"
                        ${field(this.binder.model.email)}
                ></vaadin-text-field>
                <vaadin-button
                        @click=${this.save}
                >Save
                </vaadin-button>
            </vaadin-form-layout>
        `;
    }

    private async save() {
        await this.binder.submitTo(PersonEndpoint.save);
        this.binder.clear()
        this.people = await PersonEndpoint.findAll();
    }

    private async itemSelected(event: CustomEvent) {
        this.selectedPerson = event.detail.value as Person;
        this.binder.read(this.selectedPerson);
    }
}
