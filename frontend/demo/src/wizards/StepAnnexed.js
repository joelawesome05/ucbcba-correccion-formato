import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepAnnexed extends Component {
    constructor(props) {
        super(props);
        this.state = {
            totalPages: null
        };
    }

    onDocumentComplete = (pages) => {
        this.setState({ totalPages: pages });
    }

    render() {
        if (this.props.currentStep !== 6) {
            return null
        }

        return (
            <div className="form-group">
                <center>
                    <h4> Anexos </h4>
                    <label >Página inicial:</label>
                    <input
                        name="annexedPageStart"
                        type="number"
                        value={this.props.annexedPageStart}
                        onChange={this.props.handleChange}
                    />
                    <label >Página final:</label>
                    <input
                        name="annexedPageEnd"
                        type="number"
                        value={this.props.annexedPageEnd}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.annexedPageStart}
                        pageEnd={this.props.annexedPageEnd}
                    />
                </center>
                <button type="submit" className="btn btn-success btn-lg btn-block">Enviar</button>
            </div>
        )
    }
}

export default StepAnnexed;