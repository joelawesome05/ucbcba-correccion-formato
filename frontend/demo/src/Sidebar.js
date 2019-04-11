// @flow

import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTimes } from '@fortawesome/free-solid-svg-icons'
import { faCheck } from '@fortawesome/free-solid-svg-icons'
import Section from "./Section";
import type { T_Highlight } from "../../src/types";
type T_ManuscriptHighlight = T_Highlight;

type Props = {
  highlights: Array<T_ManuscriptHighlight>,
  basicFormatReport: Array<>,
  resetHighlights: () => void,
  coverformatErrors: Array<T_ManuscriptHighlight>,
  indexformatErrors: Array<T_ManuscriptHighlight>,
  figuretableindexformatErrors: Array<T_ManuscriptHighlight>,
  numerationformatErrors: Array<T_ManuscriptHighlight>,
  biographyformatErrors: Array<T_ManuscriptHighlight>,
  figureformatErrors: Array<T_ManuscriptHighlight>,
  tableformatErrors: Array<T_ManuscriptHighlight>
};

const updateHash = highlight => {
  location.hash = `highlight-${highlight.id}`;
};

function Sidebar({ highlights, resetHighlights, basicFormatReport,
  coverformatErrors, indexformatErrors, figuretableindexformatErrors, numerationformatErrors,
  biographyformatErrors, figureformatErrors, tableformatErrors }: Props) {
  return (
    <div className="sidebar" style={{ width: "25vw" }}>
      <div className="description" style={{ padding: "1rem" }}>
        <h2 style={{ marginBottom: "1rem" }}>Reporte general</h2>

        <p style={{ fontSize: "0.7rem" }}>
          <a href="/">
            Volver al Inicio
          </a>
        </p>
      </div>
      <hr></hr>
      <div style={{ padding: "1rem" }}>
        <h4 style={{ marginBottom: "1rem" }} > Aspetos formales</h4>
        <table className="table table-bordered">
          <thead>
            <tr>
              <th scope="col">Formato básico</th>
              <th scope="col">Cumple</th>
            </tr>
          </thead>
          <tbody>
            {basicFormatReport.map((basicFormat, index) => (
              <tr
                key={index}
              >
                <td>{basicFormat.format}</td>
                {basicFormat.correct == true ? (
                  <td style={{ color: "green" }}> <FontAwesomeIcon icon={faCheck} /></td>
                ) : (<td style={{ color: "red" }}> <FontAwesomeIcon icon={faTimes} /> </td>)}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <hr></hr>
      <Section
        section="Carátula"
        formatErros={coverformatErrors}
      />

      <hr></hr>
      <Section
        section="Índice General"
        formatErros={indexformatErrors}
      />

      <hr></hr>
      <Section
        section="Índice Tablas y Figuras"
        formatErros={figuretableindexformatErrors}
      />

      <hr></hr>
      <Section
        section="Paginación"
        formatErros={numerationformatErrors}
      />

      <hr></hr>
      <Section
        section="Figuras"
        formatErros={figureformatErrors}
      />

      <hr></hr>
      <Section
        section="Tablas"
        formatErros={tableformatErrors}
      />

      <hr></hr>
      <Section
        section="Bibliografía"
        formatErros={biographyformatErrors}
      />




    </div>
  );
}

export default Sidebar;
