import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'
import { faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import { faExclamationTriangle } from '@fortawesome/free-solid-svg-icons'

class Section extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showing: false,
            section: this.props.section,
        };
    }



    render() {
        var { section, showing } = this.state;
        const updateHash = highlight => {
            location.hash = `highlight-${highlight.id}`;
        };
        var errors = [];
        var warnings = [];

        for (const [index, highlight] of this.props.formatErros.entries()) {
            if (highlight.error) {
                errors.push(<li
                    key={index}
                    className="sidebar__highlight"
                    onClick={() => {
                        updateHash(highlight);
                    }}
                >
                    <div>
                        <p>
                            <i style={{ color: "red" }}> <FontAwesomeIcon icon={faExclamationCircle} /> </i>
                            {highlight.comment.text}
                        </p>
                        {highlight.content.text ? (
                            <blockquote style={{ marginTop: "0.5rem" }}>
                                {`${highlight.content.text.slice(0, 90).trim()}…`}
                            </blockquote>
                        ) : null}
                        {highlight.content.image ? (
                            <div
                                className="highlight__image"
                                style={{ marginTop: "0.5rem" }}
                            >
                                <img src={highlight.content.image} alt={"Screenshot"} />
                            </div>
                        ) : null}
                    </div>
                    <div className="highlight__location">
                        Página {highlight.position.pageNumber}
                    </div>
                </li>)
            } else {
                warnings.push(<li
                    key={index}
                    className="sidebar__highlight"
                    onClick={() => {
                        updateHash(highlight);
                    }}
                >
                    <div>
                        <p>
                            <i style={{ color: "#ffae42" }}> <FontAwesomeIcon icon={faExclamationTriangle} /> </i>
                            {highlight.comment.text}
                        </p>
                        {highlight.content.text ? (
                            <blockquote style={{ marginTop: "0.5rem" }}>
                                {`${highlight.content.text.slice(0, 90).trim()}…`}
                            </blockquote>
                        ) : null}
                        {highlight.content.image ? (
                            <div
                                className="highlight__image"
                                style={{ marginTop: "0.5rem" }}
                            >
                                <img src={highlight.content.image} alt={"Screenshot"} />
                            </div>
                        ) : null}
                    </div>
                    <div className="highlight__location">
                        Página {highlight.position.pageNumber}
                    </div>
                </li>)
            }

        }

        return (
            <div>
                <h4 style={{ marginBottom: "1rem", padding: "1rem" }} > {section}
                    <button onClick={() => this.setState({ showing: !showing })}>
                        {showing ?
                            <FontAwesomeIcon icon={faAngleUp} />
                            :
                            <FontAwesomeIcon icon={faAngleDown} />
                        }
                    </button>
                </h4>

                {showing
                    ? <div>
                        {this.props.formatErros.length == 0 ?
                            <p> No se encontraron errores</p>
                            :
                            <ul className="sidebar__highlights">
                                {errors.length != 0 ? (
                                    <div>
                                        <center>
                                            <h5 style={{ paddingLeft: "1rem" }} >
                                                Errores
                                            <i style={{ color: "red" }}> <FontAwesomeIcon icon={faExclamationCircle} /> </i>
                                            </h5>
                                        </center>
                                        {errors}
                                    </div>
                                ) : null}
                                {warnings.length != 0 ? (
                                    <div>
                                        <center>
                                            <h5 style={{ paddingTop: "1rem" }} >
                                                Advertencias
                                            <i style={{ color: "#ffae42" }}> <FontAwesomeIcon icon={faExclamationTriangle} /> </i>
                                            </h5>
                                        </center>
                                        {warnings}
                                    </div>
                                ) : null}
                            </ul>
                        }
                    </div>
                    : null
                }
            </div>
        )
    }

}


export default Section;
