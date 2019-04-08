import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'

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
                                {this.props.formatErros.map((highlight, index) => (
                                    <li
                                        key={index}
                                        className="sidebar__highlight"
                                        onClick={() => {
                                            updateHash(highlight);
                                        }}
                                    >
                                        <div>
                                            <p><strong>Por favor verifique: </strong>{highlight.comment.text}</p>
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
                                    </li>
                                ))}
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
