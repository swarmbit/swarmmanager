import { DockerServicePort } from './../../../../../services/docker/services/docker.service.port';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { FormsService } from '../../../../../services/utils/forms.service';

@Component({
  selector: 'app-ports-form',
  templateUrl: 'ports.form.html'
})
export class PortsForm implements OnInit {

  @Input()
  public editMode: boolean;

  @Input()
  public isDetails: boolean;

  @Input()
  public disabled: boolean;

  @Input()
  public serviceForm: FormGroup;

  @Input()
  public ports: DockerServicePort[];

  public controlName = 'ports';

  constructor(public formsService: FormsService) {
  }

  ngOnInit(): void {
    const ports = new FormArray([]);
    this.serviceForm.addControl(this.controlName, ports);
    this.addPorts(this.ports);
  }

  getPorts() {
    const values = this.serviceForm.value;
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
    return ports;
  }

  getControlArray() {
    return this.formsService.getFormArray(this.serviceForm, this.controlName).controls;
  }

  showAdd(i): boolean {
    return (!this.isDetails || this.editMode)
      && i === (this.formsService.getFormArray(this.serviceForm, this.controlName).controls.length - 1);
  }

  showDelete(i): boolean {
    return (!this.isDetails || this.editMode)
      && i < (this.formsService.getFormArray(this.serviceForm, this.controlName).controls.length - 1);
  }

  add(): void {
      this.addPort(null, false);
  }

  remove(i) {
    this.formsService.removeFromArray(this.serviceForm, this.controlName, i);
  }

  private addPorts(ports: DockerServicePort[]) {
    if (ports && ports.length > 0) {
      for (const port of ports) {
        this.addPort(port, this.disabled);
      }
    }
    this.addPort(new DockerServicePort(), this.disabled);
  }

  private addPort(port: DockerServicePort, disabled: boolean): void {
    const formGroupObj = {};
    formGroupObj['protocol'] = new FormControl({value: port && port.protocol ? port.protocol : '', disabled: disabled});
    formGroupObj['published'] = new FormControl({value: port && port.published ? port.published : '', disabled: disabled});
    formGroupObj['target'] = new FormControl({value: port && port.target ? port.target : '', disabled: disabled});
    (<FormArray>this.serviceForm.get('ports')).push(new FormGroup(formGroupObj));
  }
}
