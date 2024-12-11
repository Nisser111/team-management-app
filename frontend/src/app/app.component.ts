import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { FilterHeaderComponent } from "../components/filter-header/filter-header.component";

@Component({
  selector: "app-root",
  imports: [RouterOutlet, FilterHeaderComponent],
  template: `
    <header id="app-header">
      <h1>
        Witaj <span id="username">{{ username }}</span
        >! Zarządzaj swoimi zespołami.
      </h1>
    </header>

    <app-filter-header></app-filter-header>

    <router-outlet />
  `,
  styles: [],
})
export class AppComponent {
  username = "Miłosz"; // Temp
}
