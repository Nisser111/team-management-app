import { Component } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";

@Component({
  selector: "app-teams-container",
  imports: [TeamSectionComponent],
  template: ` <app-team-section
    [teamName]="teams[4].name"
    [employees]="employees"
  ></app-team-section>`,
  styles: ``,
})
export class TeamsContainerComponent {
  // Temp
  teams = [
    {
      id: 1,
      name: "Development Team",
    },
    {
      id: 2,
      name: "Front-end team",
    },
    {
      id: 3,
      name: "Sales Team",
    },
    {
      id: 4,
      name: "HR Team",
    },
    {
      id: 5,
      name: "Design Team",
    },
  ];

  // temp
  employees: Employee[] = [
    {
      id: 1,
      firstName: "John",
      lastName: "Doe",
      email: "john.doe@example.com",
      phone: "123-456-7890",
      hireDate: "2023-01-15",
      role: "Developer",
      teamId: 1,
    },
    {
      id: 2,
      firstName: "Jane",
      lastName: "Smith",
      email: "jane.smith@example.com",
      phone: "234-567-8901",
      hireDate: "2023-02-20",
      role: "Marketing Specialist",
      teamId: 2,
    },
    {
      id: 3,
      firstName: "Emily",
      lastName: "Johnson",
      email: "emily.johnson@example.com",
      phone: "345-678-9012",
      hireDate: "2023-03-10",
      role: "Sales Associate",
      teamId: 3,
    },
    {
      id: 4,
      firstName: "Michael",
      lastName: "Brown",
      email: "michael.brown@example.com",
      phone: "456-789-0123",
      hireDate: "2023-04-05",
      role: "HR Manager",
      teamId: 4,
    },
    {
      id: 14,
      firstName: "Jakub",
      lastName: "bujak",
      email: "jakub.bujak@example.com",
      phone: "123456789",
      hireDate: "2023-10-20",
      role: "Project Manager",
      teamId: 2,
    },
  ];
}
