import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'
import { faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import { faExclamationTriangle } from '@fortawesome/free-solid-svg-icons'
import { faCheck } from '@fortawesome/free-solid-svg-icons'

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
                errors.push(highlight)
            } else {
                warnings.push(highlight)
            }
        }

        return (
            <div>
                <div>
                    <div className={showing ? 'row buttonSection activeGrey' : 'row buttonSection'} onClick={() => this.setState({ showing: !showing })}>
                        <div className="col-sm-6"> <h5> {section} </h5> </div>
                        <div className="col-sm-4 iconsSeccion">
                            {this.props.formatErros.length == 0 ? (
                                <div className="checkGreen">  <FontAwesomeIcon icon={faCheck} /> </div>
                            ) : (
                                    <div>
                                        <div className="errorsNro errorColor">  {errors.length} <FontAwesomeIcon icon={faExclamationCircle} /> </div>
                                        <div className="warningsNro warningColor">   {warnings.length} <FontAwesomeIcon icon={faExclamationTriangle} /> </div>
                                    </div>
                                )}
                        </div>
                        <div className="col-sm-2">
                            <div>
                                {showing ?
                                    <FontAwesomeIcon icon={faAngleUp} />
                                    :
                                    <FontAwesomeIcon icon={faAngleDown} />
                                }
                            </div>
                        </div>
                    </div>
                </div>

                {showing ?
                    <div>
                        {this.props.formatErros.length == 0 ?
                            null
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
                                            <p>
                                                {highlight.error ? (
                                                    <i className="errorColor"> <FontAwesomeIcon icon={faExclamationCircle} /> </i>
                                                ) : <i className="warningColor"> <FontAwesomeIcon icon={faExclamationTriangle} /> </i>}
                                                {highlight.comment.text}
                                            </p>
                                            {highlight.content.text ? (
                                                <blockquote className="contentText">
                                                    {`${highlight.content.text.slice(0, 90).trim()}…`}
                                                </blockquote>
                                            ) : null}
                                        </div>
                                        <div className="highlight__location">
                                            Página {highlight.position.pageNumber}
                                        </div>
                                        <div className="resolve">
                                            <button type="button" className="resolve btn btn-info btn-sm" onClick={this.props.removeHighlight.bind(this, highlight.id)}>Omitir</button>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        }
                    </div>
                    : null
                }
            </div >
        )
    }

}


export default Section;
