import { Component, OnInit, Inject } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { TeamService } from "../../services/teams.service";
import { Team } from "../../interfaces/Team.interface";
import { MatFormField, MatHint, MatLabel } from "@angular/material/form-field";
import { MatOption } from "@angular/material/core";
import { MatInput } from "@angular/material/input";
import { MatSelect } from "@angular/material/select";
import { MatButton } from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogRef,
  MatDialogTitle,
} from "@angular/material/dialog";
import { NgForOf, NgIf } from "@angular/common";
import { createEmployeeForm } from "./employee-form.model";
import { Employee } from "../../interfaces/Employee.interface";

@Component({
  selector: "app-add-edit-employee-modal",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatOption,
    MatInput,
    MatSelect,
    MatButton,
    MatDialogTitle,
    MatDialogActions,
    NgForOf,
    MatHint,
    NgIf,
  ],
  templateUrl: "./add-edit-employee-modal.template.html",
  styleUrls: ["./add-edit-employee-modal.scss"],
})
export class AddEditEmployeeModalComponent implements OnInit {
  employeeForm: FormGroup;
  teams: Team[] = [];
  currentEmployeeData: Employee;

  constructor(
    private fb: FormBuilder,
    private teamService: TeamService,
    public dialogRef: MatDialogRef<AddEditEmployeeModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.currentEmployeeData = this.data.currentEmployeeData;
    this.employeeForm = createEmployeeForm(
      this.fb,
      this.currentEmployeeData.id
    );
  }

  ngOnInit(): void {
    this.teamService.getTeams().subscribe({
      next: (response) => {
        const { data } = response;
        this.teams = data as Team[];
      },
      error: (error) => {
        console.error("Error fetching teams:", error);
      },
    });
  }

  onSubmit(): void {
    if (this.employeeForm.valid) {
      this.dialogRef.close(this.employeeForm.value);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
