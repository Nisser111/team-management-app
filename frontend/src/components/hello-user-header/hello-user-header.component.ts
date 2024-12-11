import { Component } from "@angular/core";

/**
 * HelloUserHeaderComponent is a standalone component that displays a welcome message
 * to the user, including their username. It serves as a header for managing teams.
 */
@Component({
  selector: "app-hello-user-header",
  standalone: true,
  template: `
    <header id="app-header">
      <h1>
        Witaj <span id="username">{{ username }}</span
        >! Zarządzaj swoimi zespołami.
      </h1>
    </header>
  `,
  styleUrls: ["../../styles/hello-user-header.scss"],
})
export class HelloUserHeaderComponent {
  username: string = "Miłosz";
}
