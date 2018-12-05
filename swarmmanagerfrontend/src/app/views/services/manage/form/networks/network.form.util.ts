import { FormControl, FormArray, FormGroup } from '@angular/forms';
import { DockerService } from '../../../../../services/docker/services/docker.service';

export class NetworkFormUtil {
  static networksControlName = 'networks';

  static endpointControlName = 'endpointMode';

  static hostsControlName = 'hosts';

  static hostsEntryName = 'host';

  static hostnameControlName = 'hostname';

  static initNetworkForm(form, formsService, service, isDisabled) {
    if (form) {
      form.addControl(this.networksControlName,  new FormArray([]));
      form.addControl(this.hostnameControlName, new FormControl({ value:
        formsService.getValue(service, NetworkFormUtil.hostnameControlName), disabled: isDisabled }));
      form.addControl(this.endpointControlName, new FormControl({ value:
        formsService.getValue(service, NetworkFormUtil.endpointControlName), disabled: isDisabled }));
      form.addControl(this.hostsControlName, new FormArray([]));

      this.addNetworksToForm(form, service, isDisabled);
      formsService
          .parseObjectFieldToOptions(form, service, this.hostsControlName, true, isDisabled, this.hostsEntryName);
    }
  }

  static addNetworks(form, formsService, service: DockerService) {
    const values = form.value;
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
    formsService.parseOptionsToObjectField(service, values, this.hostsControlName, NetworkFormUtil.hostsEntryName);
  }

  static addNetworksToForm(form, service: DockerService, disabled) {
    if (service && service.networks && service.networks.length > 0) {
      for (const network of service.networks) {
        this.addNetworkToForm(form, network, disabled);
      }
    }
    this.addNetworkToForm(form, '', disabled);
  }

  static addNetworkToForm(form, network: string, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['id'] = new FormControl({value: network ? network : '', disabled: disabled});
    (<FormArray>form.get(this.networksControlName)).push(new FormGroup(formGroupObj));
  }

}
