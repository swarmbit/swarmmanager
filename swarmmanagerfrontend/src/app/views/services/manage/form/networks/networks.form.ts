import { DockerNetworkSummary } from './../../../../../services/docker/networks/docker.network.summary';
import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormsService } from '../../../../../services/utils/forms.service';
import { DockerSwarmService } from '../../../../../services/docker/swarms/docker.swarms.service';
import { DockerNetworksService } from '../../../../../services/docker/networks/docker.networks.service';
import { Subscription } from 'rxjs';
import { NetworkFormUtil } from './network.form.util';

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
  public expanded: boolean;

  public networksControlName = NetworkFormUtil.networksControlName;

  public endpointControlName = NetworkFormUtil.endpointControlName;

  public hostsControlName = NetworkFormUtil.hostsControlName;

  public hostsEntryName = NetworkFormUtil.hostsEntryName;

  public hostnameControlName = NetworkFormUtil.hostnameControlName;

  networks: DockerNetworkSummary[] = [];

  subscriptions: Subscription[] = [];

  public addNetwork = () => {
    NetworkFormUtil.addNetworkToForm(this.serviceForm, '', this.isDetails && !this.editMode);
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
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    });
  }

}
