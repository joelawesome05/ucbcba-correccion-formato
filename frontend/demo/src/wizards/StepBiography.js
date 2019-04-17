import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepBiography extends Component {
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
        if (this.props.currentStep !== 5) {
            return null
        }

        return (
            <div className="form-group">
                <center><h4> Bibliografía </h4>
                    <label >Página inicial:</label>
                    <input
                        name="biographyPageStart"
                        type="number"
                        value={this.props.biographyPageStart}
                        onChange={this.props.handleChange}
                    />
                    <label >Página final:</label>
                    <input
                        name="biographyPageEnd"
                        type="number"
                        value={this.props.biographyPageEnd}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.biographyPageStart}
                        pageEnd={this.props.biographyPageEnd}
                    />
                </center>
            </div>
        )
    }
}

export default StepBiography;
