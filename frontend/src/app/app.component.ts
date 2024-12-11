import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { FilterHeaderComponent } from "../components/filter-header/filter-header.component";
import { HelloUserHeaderComponent } from "../components/hello-user-header/hello-user-header.component";

@Component({
  selector: "app-root",
  imports: [RouterOutlet, FilterHeaderComponent, HelloUserHeaderComponent],
  template: `
    <app-hello-user-header></app-hello-user-header>

    <main>
      <app-filter-header></app-filter-header>
    </main>

    <router-outlet />
  `,
})
export class AppComponent {}
