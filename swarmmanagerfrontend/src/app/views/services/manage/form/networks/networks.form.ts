import { DockerNetworkSummary } from './../../../../../services/docker/networks/docker.network.summary';
import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { FormsService } from '../../../../../services/utils/forms.service';
import { DockerService } from '../../../../../services/docker/services/docker.service';
import { DockerSwarmService } from '../../../../../services/docker/swarms/docker.swarms.service';
import { DockerNetworksService } from '../../../../../services/docker/networks/docker.networks.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-networks-form',
  templateUrl: 'networks.form.html'
})
export class NetworksForm implements OnInit, OnDestroy {

  @Input()
  public editMode: boolean;

  @Input()
  public isDetails: boolean;

  @Input()
  public disabled: boolean;

  @Input()
  public serviceForm: FormGroup;

  @Input()
  public service: DockerService;

  public networksControlName = 'networks';

  public endpointControlName = 'endpointMode';

  public hostsControlName = 'hosts';

  public hostsEntryName = 'host';

  public hostnameControlName = 'hostname';

  networks: DockerNetworkSummary[] = [];

  subscriptions: Subscription[] = [];

  public addNetwork = () => {
    this.addNetworkToForm('', this.isDetails && !this.editMode);
  }

  constructor(public formsService: FormsService,
              public swarmService: DockerSwarmService,
              public networkService: DockerNetworksService
    ) {
  }

  ngOnInit(): void {
    this.subscriptions.push(this.swarmService.onSwarmChange().subscribe(() => {
      this.networkService.getNetworksList(true).toPromise().then(
        networks => {
          this.networks = [];
          for (const network of networks) {
            if (network.name !== 'ingress') {
              this.networks.push(network);
            }
          }
        }
      );
    }));
    this.serviceForm.addControl(this.networksControlName,  new FormArray([]));
    this.serviceForm.addControl(this.hostnameControlName, new FormControl({ value: this.service.hostname, disabled: this.disabled }));
    this.serviceForm.addControl(this.endpointControlName, new FormControl({ value: this.service.endpointMode, disabled: this.disabled }));
    this.serviceForm.addControl(this.hostsControlName, new FormArray([]));
    this.addNetworksToForm(this.service);
    this.formsService
        .parseObjectFieldToOptions(this.serviceForm, this.service, this.hostsControlName, true, this.disabled, this.hostsEntryName);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    });
  }

  addNetworks(service: DockerService) {
    const values = this.serviceForm.value;
    const networksValues = values[this.networksControlName];
    const networks = [];
    for (const networkValue of networksValues) {
      const id = networkValue['id'];
      if (id !== '') {
        networks.push(id);
      }
    }
    service.networks = networks;
    service.hostname = values[this.hostnameControlName];
    service.endpointMode = values[this.endpointControlName];
    this.formsService.parseOptionsToObjectField(service, values, this.hostsControlName, 'host');
  }

  private addNetworksToForm(service: DockerService) {
    if (service.networks && service.networks.length > 0) {
      for (const network of service.networks) {
        this.addNetworkToForm(network, this.disabled);
      }
    }
    this.addNetworkToForm('', this.disabled);
  }

  addNetworkToForm(network: string, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['id'] = new FormControl({value: network ? network : '', disabled: disabled});
    (<FormArray>this.serviceForm.get(this.networksControlName)).push(new FormGroup(formGroupObj));
  }
}
