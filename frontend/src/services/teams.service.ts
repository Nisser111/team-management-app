import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Team } from "../interfaces/Team.interface";

@Injectable({
  providedIn: "root",
})
export class TeamService {
  private apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient) {}

  getTeams(): Observable<Team[]> {
    const headers = {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    };
    return this.http.get<Team[]>(`${this.apiUrl}/teams`, { headers });
  }
}
