import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";

@Component({
  selector: "app-root",
  imports: [RouterOutlet],
  template: `
    <h1>{{ title }}</h1>

    <router-outlet />
  `,
  styles: [
    `
      h1 {
        text-align: center;
      }
    `,
  ],
})
export class AppComponent {
  title = "Witaj! Zarządzaj swoimi zespołami.";
}
