import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Team } from "../interfaces/Team.interface";
import { map } from "rxjs/operators";
import { ApiResponse } from "../interfaces/ApiResponse.interface";

@Injectable({
  providedIn: "root",
})
export class TeamService {
  private apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient) {}

  private getHeaders() {
    return {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    };
  }

  /**
   * Fetches a list of all teams from the server.
   *
   * This method sends a GET request to the server to retrieve a list of all teams.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @returns An Observable that resolves to an ApiResponse object containing an array of Team objects.
   */
  getTeams(): Observable<ApiResponse> {
    return this.http
      .get<ApiResponse>(`${this.apiUrl}/teams`, this.getHeaders())
      .pipe(map((response) => response));
  }

  /**
   * Adds a new team to the server.
   *
   * This method sends a POST request to the server to create a new team. The request body includes the name of the team.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param newTeamName - The name of the new team to be added.
   * @returns An Observable that resolves to an ApiResponse object containing the added Team object.
   */
  addTeam(newTeamName: string): Observable<ApiResponse> {
    return this.http
      .post<ApiResponse>(
        `${this.apiUrl}/teams`,
        { name: newTeamName },
        this.getHeaders()
      )
      .pipe(map((response) => response));
  }

  /**
   * Updates an existing team by its ID.
   *
   * This method sends a PATCH request to the server to update an existing team. The request body includes the updated name of the team.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param id - The ID of the team to be updated.
   * @param newName - The updated name of the team.
   * @returns An Observable that resolves to an ApiResponse object containing the updated Team object.
   */
  updateTeam(id: number, newName: string): Observable<ApiResponse> {
    return this.http
      .patch<ApiResponse>(
        `${this.apiUrl}/teams/${id}`,
        { name: newName },
        this.getHeaders()
      )
      .pipe(map((response) => response));
  }

  /**
   * Deletes a team by its ID.
   *
   * This method sends a DELETE request to the server to remove a team. The request includes the ID of the team to be deleted.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param id - The ID of the team to be deleted.
   * @returns An Observable that resolves to an ApiResponse object containing the deletion status.
   */
  deleteTeam(id: number): Observable<ApiResponse> {
    return this.http
      .delete<ApiResponse>(`${this.apiUrl}/teams/${id}`, this.getHeaders())
      .pipe(map((response) => response));
  }
}
