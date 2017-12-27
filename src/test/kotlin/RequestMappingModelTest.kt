import com.intellij.openapi.command.impl.DummyProject
import com.viartemev.requestmapper.RequestMappingModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RequestMappingModelTest {

    private val requestMappingModel = RequestMappingModel(DummyProject.getInstance())

    @Test
    fun `one slash`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
    }

    @Test
    fun `word without slash`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
    }

    @Test
    fun `one first part of path`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api"))
    }

    @Test
    fun `one middle part of path`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product"))
    }

    @Test
    fun `two first parts of path`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api/v1"))
    }

    @Test
    fun `two middle parts of path with slash on the end`() {
        assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product/123"))
    }
}