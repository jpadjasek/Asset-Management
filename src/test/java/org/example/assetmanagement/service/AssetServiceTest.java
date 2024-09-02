package org.example.assetmanagement.service;

import org.example.assetmanagement.dto.AssetDTO;
import org.example.assetmanagement.exception.NotFoundException;
import org.example.assetmanagement.mapper.AssetMapper;
import org.example.assetmanagement.model.Asset;
import org.example.assetmanagement.repository.AssetRepository;
import org.example.assetmanagement.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.assetmanagement.utils.TestHelper.ASSET_DESCRIPTION;
import static org.example.assetmanagement.utils.TestHelper.ASSET_ID;
import static org.example.assetmanagement.utils.TestHelper.ASSET_NAME;
import static org.example.assetmanagement.utils.TestHelper.ASSET_TYPE;
import static org.example.assetmanagement.utils.TestHelper.GROUP_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {
    private static final String NEW_NAME = "NEW NAME";
    private static final String NEW_DESC = "NEW DESC";
    private static final String NEW_TYPE = "NEW TYPE";

    @Mock
    private AssetRepository assetRepository;

    private AssetService assetService;

    @BeforeEach
    void setUp() {
        assetService = new AssetService(assetRepository);
    }

    @Test
    void shouldCreateNewAsset() {

        //given
        Asset testObject = Asset.createAsset(ASSET_NAME, ASSET_DESCRIPTION, ASSET_TYPE);
        AssetDTO testObjectDTO = AssetMapper.toDto(testObject);
        when(assetRepository.save(any(Asset.class))).thenReturn(testObject);

        //when
        AssetDTO result = assetService.create(testObjectDTO);

        //then
        verify(assetRepository).save(any(Asset.class));

        assertEquals(ASSET_NAME, result.name());
        assertEquals(ASSET_TYPE, result.type());
        assertEquals(ASSET_DESCRIPTION, result.description());

    }

    @Test
    void shouldUpdateAsset() {
        //given
        Asset persistedObject = TestHelper.prepareAssetObject();
        AssetDTO assetDTOWithNewData = new AssetDTO(null, NEW_NAME, NEW_DESC, NEW_TYPE, null);
        Asset expectedObject = Asset.updateAsset(persistedObject, NEW_NAME, NEW_DESC, NEW_TYPE);

        when(assetRepository.findById(ASSET_ID)).thenReturn(Optional.of(persistedObject));
        when(assetRepository.save(eq(expectedObject))).thenReturn(expectedObject);

        //when

        assetService.update(ASSET_ID, assetDTOWithNewData);

        //then
        verify(assetRepository).save(expectedObject);
        verify(assetRepository).findById(ASSET_ID);

    }

    @Test
    void shouldThrowExceptionWhenUpdateIfAssetNotFound() {
        //given
        AssetDTO assetDTOWithNewData = new AssetDTO(null, NEW_NAME, NEW_DESC, NEW_TYPE, null);

        when(assetRepository.findById(ASSET_ID)).thenThrow(NotFoundException.class);

        //when
        assertThrows(NotFoundException.class, () -> assetService.update(ASSET_ID, assetDTOWithNewData));

        //then
        verify(assetRepository).findById(ASSET_ID);
    }

    @Test
    void shouldReturnListWithOneAsset() {
        //given
        Asset assetTestObject = TestHelper.prepareAssetObject();
        when(assetRepository.findAll()).thenReturn(List.of(assetTestObject));

        //when
        List<AssetDTO> result = assetService.getAllAssets();

        //then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.contains(AssetMapper.toDto(assetTestObject)));
        verify(assetRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoAssets() {
        //given
        when(assetRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<AssetDTO> result = assetService.getAllAssets();

        //then
        assertTrue(result.isEmpty());
        verify(assetRepository).findAll();
    }

    @Test
    void shouldReturnAssetByUUID() {
        //given
        when(assetRepository.findById(ASSET_ID)).thenReturn(Optional.of(TestHelper.prepareAssetObject()));

        //when
        var result = assetService.getByUUID(ASSET_ID);

        //then
        verify(assetRepository).findById(ASSET_ID);
        assertEquals(ASSET_ID, result.assetUUID());
        assertEquals(ASSET_TYPE, result.type());
        assertEquals(ASSET_NAME, result.name());

        List<UUID> groupsUUID = result.groupUUIDs();
        assertEquals(1, groupsUUID.size());
        assertEquals(GROUP_ID, groupsUUID.get(0));
    }

    @Test
    void shouldThrowExceptionWhenGetByIdIfAssetNotFound() {
        //given
        when(assetRepository.findById(ASSET_ID)).thenThrow(NotFoundException.class);

        //when
        assertThrows(NotFoundException.class, () -> assetService.getByUUID(ASSET_ID));

        //then
        verify(assetRepository).findById(ASSET_ID);
    }

    @Test
    void shouldDeleteAsset() {
        //given
        Asset testObject = TestHelper.prepareAssetObject();
        when(assetRepository.findById(ASSET_ID)).thenReturn(Optional.of(testObject));

        //when
        assetService.delete(ASSET_ID);

        //then
        verify(assetRepository).findById(ASSET_ID);
        verify(assetRepository).delete(testObject);
    }

    @Test
    void shouldThrowExceptionWhenDeleteIfAssetNotFound() {
        //given
        when(assetRepository.findById(ASSET_ID)).thenThrow(NotFoundException.class);

        //when
        assertThrows(NotFoundException.class, () -> assetService.delete(ASSET_ID));

        //then
        verify(assetRepository).findById(ASSET_ID);
    }
}