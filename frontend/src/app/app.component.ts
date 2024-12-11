import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";

@Component({
  selector: "app-root",
  imports: [RouterOutlet],
  template: `
    <header id="app-header">
      <h1>
        Witaj <span id="username">{{ username }}</span
        >! Zarządzaj swoimi zespołami.
      </h1>
    </header>

    <router-outlet />
  `,
  styles: [],
})
export class AppComponent {
  username = "Miłosz"; // Temp
}
