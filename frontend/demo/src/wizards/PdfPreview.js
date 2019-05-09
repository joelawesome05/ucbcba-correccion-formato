import React, { Component } from 'react';
import PDF from 'react-pdf-js';

class PdfPreview extends Component {

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
        if (this.props.active) {
            return (<center>
                <p> No tengo esta sección </p>
            </center>)
        } else {
            if (!this.props.pageStart || !this.props.pageEnd) {
                return (
                    <center>
                        <p> Esperando por el rango de páginas. </p>
                    </center>
                )
            }
        };

        if (this.state.totalPages != null) {
            if ((this.props.pageStart > this.props.pageEnd) || (this.props.pageStart < 1) || (this.props.pageEnd > this.state.totalPages)) {
                return (
                    <center>
                        <p> Rango de páginas inexistentes del documento. </p>
                    </center>
                )
            }
        }

        var pdfPages = [];
        for (var i = this.props.pageStart; i <= this.props.pageEnd; i++) {
            pdfPages.push(
                <PDF
                    file={this.props.url}
                    onDocumentComplete={this.onDocumentComplete}
                    page={i}
                    scale={0.35}
                    key={i}
                />);
        }
        return (
            <center>
                {pdfPages}
            </center>
        )
    }

}

export default PdfPreview;
