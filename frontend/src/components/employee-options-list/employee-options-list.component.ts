import { Component, Input, Output } from "@angular/core";
import { MatIcon } from "@angular/material/icon";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { Employee } from "../../interfaces/Employee.interface";
import { EventEmitter } from "@angular/core";
import { MatButton, MatButtonModule } from "@angular/material/button";

@Component({
  selector: "app-employee-options-list",
  imports: [MatMenu, MatIcon, MatMenuTrigger, MatButtonModule, MatMenuItem],
  template: `
    <button
      mat-icon-button
      [matMenuTriggerFor]="menu"
      aria-label="Przycisk opcji"
      type="button"
    >
      <mat-icon>more_vert</mat-icon>
    </button>
    <mat-menu #menu="matMenu">
      <button mat-menu-item (click)="onEdit()">Edytuj</button>
      <button mat-menu-item (click)="onMove()">Przenieś</button>
      <button class="delete-menu-item" mat-menu-item (click)="onDelete()">
        Usuń
      </button>
    </mat-menu>
  `,
  styleUrls: ["../../styles/employee-options-list.scss"],
})
export class EmployeeOptionsListComponent {
  @Input() employee!: Employee;
  @Output() edit = new EventEmitter<Employee>();
  @Output() move = new EventEmitter<Employee>();
  @Output() delete = new EventEmitter<Employee>();

  onEdit() {
    this.edit.emit(this.employee);
  }

  onMove() {
    this.move.emit(this.employee);
  }

  onDelete() {
    this.delete.emit(this.employee);
  }
}
