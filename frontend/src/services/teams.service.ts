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

  /**
   * Retrieves a list of all teams from the server.
   *
   * @returns An Observable that resolves to an array of Team objects.
   */
  getTeams(): Observable<Team[]> {
    const headers = {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    };
    return this.http.get<Team[]>(`${this.apiUrl}/teams`, { headers });
  }

  /**
   * Posts a new team to the server.
   *
   * @param team - The Team object to be added.
   * @returns An Observable that resolves to the added Team object.
   */
  addTeam(team: Team): Observable<Team> {
    return this.http.post<Team>(`${this.apiUrl}/teams`, team, {
      headers: { "Content-Type": "application/json" },
    });
  }

  /**
   * Updates an existing team by its ID.
   *
   * @param id - The ID of the team to be updated.
   * @param team - The updated Team object.
   * @returns An Observable that resolves to the updated Team object.
   */
  updateTeam(id: string, team: Team): Observable<Team> {
    return this.http.patch<Team>(`${this.apiUrl}/teams/${id}`, team, {
      headers: { "Content-Type": "application/json" },
    });
  }

  /**
   * Deletes a team by its ID.
   *
   * @param id - The ID of the team to be deleted.
   * @returns An Observable that resolves to void.
   */
  deleteTeam(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/teams/${id}`, {
      headers: { "Content-Type": "application/json" },
    });
  }
}
