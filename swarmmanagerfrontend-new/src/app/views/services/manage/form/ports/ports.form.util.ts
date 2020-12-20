import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { DockerService } from '../../../../../services/docker/services/docker.service';
import { DockerServicePort } from './../../../../../services/docker/services/docker.service.port';

export class PortsFormUtil {
  static controlName = 'ports';

  static initPortsForm(form, ports, disabled) {
    if (form) {
      form.addControl(PortsFormUtil.controlName, new FormArray([]));
      PortsFormUtil.addPortsToForm(form, ports, disabled);
    }
  }

  static addPortsToForm(form, ports: DockerServicePort[], disabled) {
    if (ports && ports.length > 0) {
      for (const port of ports) {
        this.addPort(form, port, disabled);
      }
    }
    this.addPort(form, new DockerServicePort(), disabled);
  }

  static addPort(form, port: DockerServicePort, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['protocol'] = new FormControl({value: port && port.protocol ? port.protocol : '', disabled: disabled});
    formGroupObj['published'] = new FormControl({value: port && port.published ? port.published : '', disabled: disabled});
    formGroupObj['target'] = new FormControl({value: port && port.target ? port.target : '', disabled: disabled});
    (<FormArray>form.get(PortsFormUtil.controlName)).push(new FormGroup(formGroupObj));
  }

  static addPorts(form, service: DockerService) {
    const values = form.value;
    const portsValues = values['ports'];
    const ports = [];
    for (const portValue of portsValues) {
      const protocol = portValue['protocol'];
      const target = portValue['target'];
      const published = portValue['published'];
      if (protocol !== '' || target !== '' || published !== '') {
        const port = new DockerServicePort();
        port.protocol = protocol;
        port.target = target;
        port.published = published;
        ports.push(port);
      }
    }
    service.ports = ports;
  }

}
