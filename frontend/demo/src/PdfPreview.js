import React, { Component } from 'react';
import PDF from 'react-pdf-js';

class PdfPreview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            urlPdf: this.props.url,
            pageStart: this.props.pageStart,
            pageEnd: this.props.pageEnd,
            section: this.props.section
        };
    }

    handlePrevious = () => {
        this.setState({ page: this.state.page - 1 });
    }

    handleNext = () => {
        this.setState({ page: this.state.page + 1 });
    }

    updatePdfPreview = (event) => {
        event.preventDefault();
        this.setState({ pageStart: this.props.pageStart, pageEnd: this.props.pageEnd, page: this.props.pageStart });
    }

    onDocumentComplete = (pages) => {
        this.setState({ page: this.state.pageStart, totalPages: pages });
    }

    renderPagination = (currentPage, pageStart, pages) => {
        let previousButton = <li className="page-item" onClick={this.handlePrevious}><a className="page-link">Anterior</a></li>;
        if (currentPage === pageStart) {
            previousButton = <li className="page-item disabled"><a className="page-link" > Anterior</a></li>;
        }
        let nextButton = <li className="page-item" onClick={this.handleNext}><a className="page-link" >Siguiente </a></li>;
        if (currentPage === pages) {
            nextButton = <li className="page-item disabled"><a className="page-link" >Siguiente </a></li>;
        }
        return (
            <div>
                <p> {currentPage - pageStart + 1} de {pages - pageStart + 1}</p>
                <nav aria-label="Page navigation example">
                    <ul className="pagination">
                        {previousButton}
                        {nextButton}
                    </ul>
                </nav>
            </div>
        );
    }


    render() {
        let pagination = null;
        if (this.state.totalPages) {
            if ((this.state.pageStart > this.state.pageEnd) || (this.state.pageStart < 1) || (this.state.pageEnd > this.state.totalPages)) {
                return (
                    <div className="row">
                        <div className="col">
                            <a onClick={this.updatePdfPreview} href="#" >Actualizar Preview</a>
                            <p> No pudo encontar la sección {this.state.section} o esta sección no se encuentra en el documento. </p>
                        </div>
                    </div>
                )
            }
            pagination = this.renderPagination(this.state.page, this.state.pageStart, this.state.pageEnd);
        }
        return (
            <div className="row">
                <div className="col">
                    <a onClick={this.updatePdfPreview} href="#" >Actualizar Preview</a>
                    {pagination}
                </div>
                <div className="col">
                    <PDF
                        file={this.state.urlPdf}
                        onDocumentComplete={this.onDocumentComplete}
                        page={this.state.page}
                        scale={0.3}
                    />
                </div>
            </div>
        )
    }

}

export default PdfPreview;
